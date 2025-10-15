package example;

import com.theokanning.openai.response.CreateResponseRequest;
import com.theokanning.openai.response.InputValue;
import com.theokanning.openai.response.Response;
import com.theokanning.openai.response.stream.OutputItemAddedEvent;
import com.theokanning.openai.response.stream.OutputTextDeltaEvent;
import com.theokanning.openai.response.stream.OutputTextDoneEvent;
import com.theokanning.openai.response.stream.ResponseCompletedEvent;
import com.theokanning.openai.response.stream.ResponseCreatedEvent;
import com.theokanning.openai.response.stream.ResponseInProgressEvent;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.service.response_stream.ResponseEventHandler;
import com.theokanning.openai.service.response_stream.ResponseStreamManager;

import java.time.Duration;

/**
 * Response API Example
 * Demonstrates both streaming and non-streaming usage of Response API.
 *
 * @author bella-openai4j
 */
public class ResponseApiExample {

    public static void main(String[] args) {
        printLine('=', 60);
        System.out.println("Response API Examples");
        printLine('=', 60);

        // Initialize service with longer timeout
        OpenAiService service = new OpenAiService("qaekDD2hBoZE4ArZZlOQ9fYTQ74Qc8mq", Duration.ofMinutes(5), "http://localhost:8087/v1/");

        // Example 1: Simple non-streaming request
        example1NonStreaming(service);

        System.out.println();
        printLine('-', 60);
        System.out.println();

        // Example 2: Streaming with simple handler
        example2StreamingSimple(service);

        System.out.println();
        printLine('-', 60);
        System.out.println();

        // Example 3: Streaming with full event handler
        example3StreamingAdvanced(service);

        System.out.println();
        printLine('-', 60);
        System.out.println();

        System.out.println();
        printLine('=', 60);
        System.out.println("All examples completed!");
        printLine('=', 60);
    }

    /**
     * Helper method to print a line of repeated characters (Java 8 compatible)
     */
    private static void printLine(char ch, int count) {
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            sb.append(ch);
        }
        System.out.println(sb);
    }

    /**
     * Example 1: Non-streaming Response API
     */
    static void example1NonStreaming(OpenAiService service) {
        System.out.println("Example 1: Non-Streaming Mode");
        printLine('-', 60);

        CreateResponseRequest request = CreateResponseRequest.builder()
                .model("gpt-4o-mini")
                .input(InputValue.of("Tell me a fun fact about programming"))
                .instructions("Be concise and interesting")
                .temperature(0.8)
                .maxOutputTokens(100)
                .build();

        System.out.println("Sending request...");
        Response response = service.createResponse(request);

        System.out.println("Response ID: " + response.getId());
        System.out.println("Status: " + response.getStatus());
        System.out.println("Model: " + response.getModel());

        if (response.getOutputText() != null) {
            System.out.println("\nOutput:");
            System.out.println(response.getOutputText());
        }

        if (response.getUsage() != null) {
            System.out.println("\nToken Usage:");
            System.out.println("  Input: " + response.getUsage().getInputTokens());
            System.out.println("  Output: " + response.getUsage().getOutputTokens());
            System.out.println("  Total: " + response.getUsage().getTotalTokens());
        }
    }

    /**
     * Example 2: Simple Streaming
     */
    static void example2StreamingSimple(OpenAiService service) {
        System.out.println("Example 2: Streaming Mode (Simple)");
        printLine('-', 60);

        CreateResponseRequest request = CreateResponseRequest.builder()
                .model("gpt-4o-mini")
                .input(InputValue.of("Write a haiku about coffee"))
                .stream(true)
                .build();

        System.out.println("Streaming response:\n");

        ResponseStreamManager manager = ResponseStreamManager.start(
                service.createResponseStream(request),
                new ResponseEventHandler() {
                    @Override
                    public void onOutputTextDelta(OutputTextDeltaEvent event) {
                        if (event.getDelta() != null) {
                            System.out.print(event.getDelta());
                        }
                    }

                    @Override
                    public void onResponseCompleted(ResponseCompletedEvent event) {
                        System.out.println("\n\n[Stream completed]");
                    }
                }
        );

        manager.waitForCompletion();

        String fullText = manager.getAccumulatedText().orElse("");
        System.out.println("Total characters received: " + fullText.length());
    }

    /**
     * Example 3: Advanced Streaming with Full Event Handling
     */
    static void example3StreamingAdvanced(OpenAiService service) {
        System.out.println("Example 3: Streaming Mode (Advanced)");
        printLine('-', 60);

        CreateResponseRequest request = CreateResponseRequest.builder()
                .model("gpt-4o-mini")
                .input(InputValue.of("Explain what is machine learning in simple terms"))
                .instructions("Explain clearly for beginners")
                .stream(true)
                .build();

        System.out.println("Starting advanced stream with event tracking...\n");

        ResponseStreamManager manager = ResponseStreamManager.start(
                service.createResponseStream(request),
                new ResponseEventHandler() {
                    private int textDeltaCount = 0;

                    @Override
                    public void onResponseCreated(ResponseCreatedEvent event) {
                        System.out.println("[EVENT] Response created");
                    }

                    @Override
                    public void onResponseInProgress(ResponseInProgressEvent event) {
                        System.out.println("[EVENT] Response in progress");
                    }

                    @Override
                    public void onOutputItemAdded(OutputItemAddedEvent event) {
                        System.out.println("[EVENT] Output item added\n");
                    }

                    @Override
                    public void onOutputTextDelta(OutputTextDeltaEvent event) {
                        textDeltaCount++;
                        if (event.getDelta() != null) {
                            System.out.print(event.getDelta());
                        }
                    }

                    @Override
                    public void onOutputTextDone(OutputTextDoneEvent event) {
                        System.out.println("\n\n[EVENT] Output text done");
                        System.out.println("[INFO] Total text deltas: " + textDeltaCount);
                    }

                    @Override
                    public void onResponseCompleted(ResponseCompletedEvent event) {
                        System.out.println("[EVENT] Response completed");
                        if (event.getResponse() != null && event.getResponse().getUsage() != null) {
                            Response.Usage usage = event.getResponse().getUsage();
                            System.out.println("[INFO] Token usage - In: " + usage.getInputTokens() +
                                    ", Out: " + usage.getOutputTokens() +
                                    ", Total: " + usage.getTotalTokens());
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        System.err.println("[ERROR] " + error.getMessage());
                    }
                }
        );

        manager.waitForCompletion();
        System.out.println("\nStream manager completed: " + manager.isCompleted());
    }
}
