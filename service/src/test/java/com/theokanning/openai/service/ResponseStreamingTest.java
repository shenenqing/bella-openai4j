package com.theokanning.openai.service;

import com.theokanning.openai.response.*;
import com.theokanning.openai.response.stream.*;
import com.theokanning.openai.service.response_stream.ResponseEventHandler;
import com.theokanning.openai.service.response_stream.ResponseSSE;
import com.theokanning.openai.service.response_stream.ResponseStreamManager;
import io.reactivex.Flowable;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Response API Streaming Test
 * Tests Response API streaming functionality with SSE protocol.
 *
 * @author bella-openai4j
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResponseStreamingTest {

    static OpenAiService service;

    @BeforeAll
    static void setup() {
        service = new OpenAiService(Duration.ofSeconds(60));
    }

    @Test
    @Order(1)
    @DisplayName("Stream response - simple text generation")
    void testStreamResponseSimple() throws InterruptedException {
        CreateResponseRequest request = CreateResponseRequest.builder()
                .model("gpt-4o-mini")
                .input(InputValue.of("Count from 1 to 5"))
                .stream(true)
                .build();

        AtomicInteger eventCount = new AtomicInteger(0);
        AtomicBoolean completed = new AtomicBoolean(false);
        CountDownLatch latch = new CountDownLatch(1);

        Flowable<ResponseSSE> stream = service.createResponseStream(request);

        stream.subscribe(
                sse -> {
                    eventCount.incrementAndGet();
                    System.out.println("Event: " + sse.getType());

                    if (sse.isDone()) {
                        completed.set(true);
                    }
                },
                error -> {
                    System.err.println("Stream error: " + error.getMessage());
                    latch.countDown();
                },
                latch::countDown
        );

        assertTrue(latch.await(60, TimeUnit.SECONDS), "Stream should complete within 60 seconds");
        assertTrue(eventCount.get() > 0, "Should receive at least one event");
        assertTrue(completed.get(), "Should receive completion event");

        System.out.println("Total events received: " + eventCount.get());
    }

    @Test
    @Order(2)
    @DisplayName("Stream response - with ResponseStreamManager")
    void testStreamWithManager() throws InterruptedException {
        CreateResponseRequest request = CreateResponseRequest.builder()
                .model("gpt-4o-mini")
                .input(InputValue.of("Write a haiku about programming"))
                .stream(true)
                .build();

        AtomicInteger textDeltaCount = new AtomicInteger(0);
        StringBuilder accumulatedText = new StringBuilder();

        ResponseStreamManager manager = ResponseStreamManager.start(
                service.createResponseStream(request),
                new ResponseEventHandler() {
                    @Override
                    public void onResponseCreated(ResponseCreatedEvent event) {
                        System.out.println("Response created");
                    }

                    @Override
                    public void onOutputTextDelta(OutputTextDeltaEvent event) {
                        textDeltaCount.incrementAndGet();
                        if (event.getDelta() != null) {
                            accumulatedText.append(event.getDelta());
                            System.out.print(event.getDelta());
                        }
                    }

                    @Override
                    public void onResponseCompleted(ResponseCompletedEvent event) {
                        System.out.println("\n\nResponse completed!");
                    }

                    @Override
                    public void onError(Throwable error) {
                        System.err.println("Error: " + error.getMessage());
                    }
                }
        );

        manager.waitForCompletion();

        assertTrue(manager.isCompleted(), "Manager should be completed");
        assertTrue(textDeltaCount.get() > 0, "Should receive text deltas");
        assertTrue(accumulatedText.length() > 0, "Should accumulate text");

        // Check accumulated text from manager
        String managerText = manager.getAccumulatedText().orElse("");
        assertEquals(accumulatedText.toString(), managerText, "Accumulated text should match");

        System.out.println("Text delta count: " + textDeltaCount.get());
        System.out.println("Final text length: " + accumulatedText.length());
    }

    @Test
    @Order(3)
    @DisplayName("Stream response - synchronous processing")
    void testStreamSynchronous() {
        CreateResponseRequest request = CreateResponseRequest.builder()
                .model("gpt-4o-mini")
                .input(InputValue.of("Say hello"))
                .stream(true)
                .build();

        AtomicBoolean started = new AtomicBoolean(false);
        AtomicBoolean completed = new AtomicBoolean(false);

        ResponseStreamManager manager = ResponseStreamManager.syncStart(
                service.createResponseStream(request),
                new ResponseEventHandler() {
                    @Override
                    public void onResponseInProgress(ResponseInProgressEvent event) {
                        started.set(true);
                    }

                    @Override
                    public void onResponseCompleted(ResponseCompletedEvent event) {
                        completed.set(true);
                    }
                }
        );

        // syncStart blocks until completion, so we should be done here
        assertTrue(manager.isCompleted(), "Should be completed after syncStart");
        assertTrue(completed.get(), "Should have received completion event");

        System.out.println("Synchronous stream completed");
    }

    @Test
    @Order(4)
    @DisplayName("Stream response - handle all major event types")
    void testStreamEventTypes() throws InterruptedException {
        CreateResponseRequest request = CreateResponseRequest.builder()
                .model("gpt-4o-mini")
                .input(InputValue.of("Explain what is AI in one sentence"))
                .stream(true)
                .build();

        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger createdEvents = new AtomicInteger(0);
        AtomicInteger inProgressEvents = new AtomicInteger(0);
        AtomicInteger textDeltaEvents = new AtomicInteger(0);
        AtomicInteger completedEvents = new AtomicInteger(0);

        ResponseStreamManager manager = ResponseStreamManager.start(
                service.createResponseStream(request),
                new ResponseEventHandler() {
                    @Override
                    public void onResponseCreated(ResponseCreatedEvent event) {
                        createdEvents.incrementAndGet();
                    }

                    @Override
                    public void onResponseInProgress(ResponseInProgressEvent event) {
                        inProgressEvents.incrementAndGet();
                    }

                    @Override
                    public void onOutputTextDelta(OutputTextDeltaEvent event) {
                        textDeltaEvents.incrementAndGet();
                    }

                    @Override
                    public void onResponseCompleted(ResponseCompletedEvent event) {
                        completedEvents.incrementAndGet();
                    }

                    @Override
                    public void onEnd() {
                        latch.countDown();
                    }

                    @Override
                    public void onError(Throwable error) {
                        System.err.println("Stream error: " + error.getMessage());
                        latch.countDown();
                    }
                }
        );

        assertTrue(latch.await(60, TimeUnit.SECONDS), "Stream should complete");

        System.out.println("Event counts:");
        System.out.println("  Created: " + createdEvents.get());
        System.out.println("  In Progress: " + inProgressEvents.get());
        System.out.println("  Text Deltas: " + textDeltaEvents.get());
        System.out.println("  Completed: " + completedEvents.get());

        assertTrue(completedEvents.get() > 0, "Should receive at least one completed event");
    }

    @Test
    @Order(5)
    @DisplayName("Stream response - with instructions and parameters")
    void testStreamWithInstructions() throws InterruptedException {
        CreateResponseRequest request = CreateResponseRequest.builder()
                .model("gpt-4o-mini")
                .input(InputValue.of("Tell me a joke"))
                .instructions("You are a comedian. Be funny but keep it clean.")
                .temperature(0.9)
                .maxOutputTokens(100)
                .stream(true)
                .build();

        CountDownLatch latch = new CountDownLatch(1);
        StringBuilder fullResponse = new StringBuilder();

        ResponseStreamManager manager = ResponseStreamManager.start(
                service.createResponseStream(request),
                new ResponseEventHandler() {
                    @Override
                    public void onOutputTextDelta(OutputTextDeltaEvent event) {
                        if (event.getDelta() != null) {
                            fullResponse.append(event.getDelta());
                        }
                    }

                    @Override
                    public void onResponseCompleted(ResponseCompletedEvent event) {
                        latch.countDown();
                    }

                    @Override
                    public void onError(Throwable error) {
                        latch.countDown();
                    }
                }
        );

        assertTrue(latch.await(60, TimeUnit.SECONDS), "Stream should complete");
        assertTrue(fullResponse.length() > 0, "Should receive response text");

        System.out.println("Response with instructions completed");
        System.out.println("Response length: " + fullResponse.length());
    }

    @Test
    @Order(6)
    @DisplayName("Stream response - raw SSE events")
    void testRawSseEvents() throws InterruptedException {
        CreateResponseRequest request = CreateResponseRequest.builder()
                .model("gpt-4o-mini")
                .input(InputValue.of("Say 'test'"))
                .stream(true)
                .build();

        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger totalEvents = new AtomicInteger(0);

        service.createResponseStream(request).subscribe(
                sse -> {
                    totalEvents.incrementAndGet();
                    BaseStreamEvent event = sse.getEvent();

                    assertNotNull(event, "Event should not be null");
                    assertNotNull(event.getType(), "Event type should not be null");

                    System.out.println("Event type: " + event.getType());
                },
                error -> {
                    System.err.println("Error: " + error.getMessage());
                    latch.countDown();
                },
                latch::countDown
        );

        assertTrue(latch.await(60, TimeUnit.SECONDS), "Stream should complete");
        assertTrue(totalEvents.get() > 0, "Should receive events");

        System.out.println("Total raw SSE events: " + totalEvents.get());
    }
}
