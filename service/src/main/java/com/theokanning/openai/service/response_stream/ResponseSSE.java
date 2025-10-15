package com.theokanning.openai.service.response_stream;

import com.theokanning.openai.response.stream.BaseStreamEvent;
import com.theokanning.openai.response.stream.ResponseCompletedEvent;
import com.theokanning.openai.response.stream.ResponseFailedEvent;
import com.theokanning.openai.utils.JsonUtil;
import lombok.Getter;

/**
 * Response API SSE (Server-Sent Events) wrapper.
 * Wraps streaming events from the Response API.
 *
 * Note: Response API does NOT send a [DONE] marker like Chat Completion API.
 * The stream ends with response.completed, response.failed, or response.incomplete events.
 *
 * @author bella-openai4j
 * @see BaseStreamEvent
 */
@Getter
public class ResponseSSE {
    private final String type;
    private final String data;

    public ResponseSSE(String type, String data) {
        this.type = type;
        this.data = data;
    }

    /**
     * Check if this is a completion event (response.completed, response.failed, etc.).
     * Response API does NOT use [DONE] marker.
     */
    public boolean isDone() {
        // Response API uses event types to indicate completion
        return "response.completed".equals(type)
            || "response.failed".equals(type)
            || "response.incomplete".equals(type);
    }

    /**
     * Parse the JSON data into a BaseStreamEvent object.
     * The actual type will be determined by the @JsonTypeInfo annotation.
     */
    public BaseStreamEvent getEvent() {
        return JsonUtil.readValue(data, BaseStreamEvent.class);
    }

    @Override
    public String toString() {
        return "ResponseSSE{" +
                "type='" + type + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
