package com.theokanning.openai.service.response_stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.OpenAiError;
import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.service.SSEFormatException;
import io.reactivex.FlowableEmitter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Callback to parse Server Sent Events (SSE) from Response API raw InputStream
 * and emit the events with io.reactivex.FlowableEmitter to allow streaming of SSE.
 *
 * @author bella-openai4j
 */
public class ResponseResponseBodyCallback implements Callback<ResponseBody> {
    private static final ObjectMapper mapper = OpenAiService.defaultObjectMapper();

    private final FlowableEmitter<ResponseSSE> emitter;

    public ResponseResponseBodyCallback(FlowableEmitter<ResponseSSE> emitter) {
        this.emitter = emitter;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        try {
            if (!response.isSuccessful()) {
                HttpException e = new HttpException(response);
                try (ResponseBody errorBody = response.errorBody()) {
                    if (errorBody == null) {
                        throw e;
                    } else {
                        OpenAiError error = mapper.readValue(
                                errorBody.string(),
                                OpenAiError.class
                        );
                        throw new OpenAiHttpException(error, e, e.code());
                    }
                }
            }

            try (InputStream in = response.body().byteStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))
            ) {
                String line;
                ResponseSSE sse = null;

                while (!emitter.isCancelled() && (line = reader.readLine()) != null) {
                    if (line.startsWith("event:")) {
                        // Parse event type
                        String eventType = line.substring(6).trim();
                        line = reader.readLine();

                        if (line != null && line.startsWith("data:")) {
                            String data = line.substring(5).trim();
                            sse = new ResponseSSE(eventType, data);
                        } else {
                            throw new SSEFormatException("Invalid SSE format! Expected 'data:' but got: " + line);
                        }
                    } else if (line.isEmpty() && sse != null) {
                        // Empty line indicates end of event
                        emitter.onNext(sse);

                        // Response API ends with response.completed/failed/incomplete events,
                        // NOT a [DONE] marker
                        if (sse.isDone()) {
                            sse = null;
                            break;
                        }
                        sse = null;
                    } else if (!line.isEmpty() && !line.startsWith(":")) {
                        // Non-empty line that's not a comment (starting with :)
                        throw new SSEFormatException("Invalid SSE format! Unexpected line: " + line);
                    }
                    // Ignore comment lines starting with ":"
                }

                // Emit last event if exists (though normally we should have hit isDone())
                if (sse != null) {
                    emitter.onNext(sse);
                }

                emitter.onComplete();
            }
        } catch (Throwable t) {
            onFailure(call, t);
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        emitter.onError(t);
    }
}
