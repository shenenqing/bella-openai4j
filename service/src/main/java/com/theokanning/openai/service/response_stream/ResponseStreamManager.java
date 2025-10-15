package com.theokanning.openai.service.response_stream;

import com.theokanning.openai.OpenAiError;
import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.response.Response;
import com.theokanning.openai.response.stream.*;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Response stream manager to handle Response API streaming events.
 * Supports synchronous/asynchronous processing of streams and event callbacks.
 *
 * @author bella-openai4j
 */
@Slf4j
public class ResponseStreamManager {
    private final ResponseEventHandler eventHandler;
    private final List<ResponseSSE> eventMsgsHolder;
    private final Flowable<ResponseSSE> stream;
    private Disposable disposable;

    @Getter
    private volatile boolean completed;

    private Response currentResponse;
    private StringBuilder accumulatedText;
    private StringBuilder accumulatedReasoningText;

    /**
     * Response stream manager.
     *
     * @param stream       A Flowable of ResponseSSE events
     * @param eventHandler Event handler for callbacks
     */
    private ResponseStreamManager(Flowable<ResponseSSE> stream, ResponseEventHandler eventHandler) {
        this.eventHandler = eventHandler;
        this.eventMsgsHolder = Collections.synchronizedList(new ArrayList<>());
        this.stream = stream;
        this.accumulatedText = new StringBuilder();
        this.accumulatedReasoningText = new StringBuilder();
    }

    /**
     * Create an asynchronous response stream manager.
     */
    private ResponseStreamManager(Flowable<ResponseSSE> stream) {
        this(stream, new ResponseEventHandler() {
        });
    }

    /**
     * Start an asynchronous stream manager with event handler.
     */
    public static ResponseStreamManager start(Flowable<ResponseSSE> stream, ResponseEventHandler eventHandler) {
        ResponseStreamManager manager = new ResponseStreamManager(stream, eventHandler);
        manager.start();
        return manager;
    }

    /**
     * Start an asynchronous stream manager without event handler.
     */
    public static ResponseStreamManager start(Flowable<ResponseSSE> stream) {
        ResponseStreamManager manager = new ResponseStreamManager(stream);
        manager.start();
        return manager;
    }

    /**
     * Start a synchronous stream manager with event handler (blocking).
     */
    public static ResponseStreamManager syncStart(Flowable<ResponseSSE> stream, ResponseEventHandler eventHandler) {
        ResponseStreamManager manager = new ResponseStreamManager(stream, eventHandler);
        manager.syncStart();
        return manager;
    }

    /**
     * Start a synchronous stream manager without event handler (blocking).
     */
    public static ResponseStreamManager syncStart(Flowable<ResponseSSE> stream) {
        ResponseStreamManager manager = new ResponseStreamManager(stream);
        manager.syncStart();
        return manager;
    }

    /**
     * Get the current response object.
     */
    public Optional<Response> getCurrentResponse() {
        return Optional.ofNullable(currentResponse);
    }

    /**
     * Get the accumulated output text.
     */
    public Optional<String> getAccumulatedText() {
        return accumulatedText.length() > 0 ? Optional.of(accumulatedText.toString()) : Optional.empty();
    }

    /**
     * Get the accumulated reasoning text.
     */
    public Optional<String> getAccumulatedReasoningText() {
        return accumulatedReasoningText.length() > 0 ? Optional.of(accumulatedReasoningText.toString()) : Optional.empty();
    }

    /**
     * Start asynchronous processing.
     */
    private void start() {
        disposable = stream.subscribe(this::handleEvent, eventHandler::onError, () -> {
            completed = true;
            eventHandler.onEnd();
        });
    }

    /**
     * Shutdown the stream manager.
     */
    public void shutDown() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /**
     * Start synchronous processing (blocking).
     */
    private void syncStart() {
        stream.blockingSubscribe(this::handleEvent, eventHandler::onError, () -> {
            completed = true;
            eventHandler.onEnd();
        });
    }

    /**
     * Handle each SSE event.
     */
    private void handleEvent(ResponseSSE sse) {
        eventMsgsHolder.add(sse);
        eventHandler.onEvent(sse);

        if (sse.isDone()) {
            completed = true;
            eventHandler.onEnd();
            return;
        }

        try {
            BaseStreamEvent event = sse.getEvent();
            if (event == null) {
                return;
            }

            String eventType = event.getType();

            // Handle different event types
            if (event instanceof ResponseCreatedEvent) {
                eventHandler.onResponseCreated((ResponseCreatedEvent) event);
            } else if (event instanceof ResponseQueuedEvent) {
                eventHandler.onResponseQueued((ResponseQueuedEvent) event);
            } else if (event instanceof ResponseInProgressEvent) {
                eventHandler.onResponseInProgress((ResponseInProgressEvent) event);
            } else if (event instanceof ResponseCompletedEvent) {
                eventHandler.onResponseCompleted((ResponseCompletedEvent) event);
            } else if (event instanceof ResponseFailedEvent) {
                log.warn("Response failed: {}", event);
                eventHandler.onResponseFailed((ResponseFailedEvent) event);
            } else if (event instanceof ResponseIncompleteEvent) {
                log.warn("Response incomplete: {}", event);
                eventHandler.onResponseIncomplete((ResponseIncompleteEvent) event);
            } else if (event instanceof OutputItemAddedEvent) {
                eventHandler.onOutputItemAdded((OutputItemAddedEvent) event);
            } else if (event instanceof OutputItemDoneEvent) {
                eventHandler.onOutputItemDone((OutputItemDoneEvent) event);
            } else if (event instanceof ContentPartAddedEvent) {
                eventHandler.onContentPartAdded((ContentPartAddedEvent) event);
            } else if (event instanceof ContentPartDoneEvent) {
                eventHandler.onContentPartDone((ContentPartDoneEvent) event);
            } else if (event instanceof OutputTextDeltaEvent) {
                OutputTextDeltaEvent textDelta = (OutputTextDeltaEvent) event;
                if (textDelta.getDelta() != null) {
                    accumulatedText.append(textDelta.getDelta());
                }
                eventHandler.onOutputTextDelta(textDelta);
            } else if (event instanceof OutputTextDoneEvent) {
                eventHandler.onOutputTextDone((OutputTextDoneEvent) event);
            } else if (event instanceof FunctionCallArgumentsDeltaEvent) {
                eventHandler.onFunctionCallArgumentsDelta((FunctionCallArgumentsDeltaEvent) event);
            } else if (event instanceof FunctionCallArgumentsDoneEvent) {
                eventHandler.onFunctionCallArgumentsDone((FunctionCallArgumentsDoneEvent) event);
            } else if (event instanceof FileSearchInProgressEvent) {
                eventHandler.onFileSearchInProgress((FileSearchInProgressEvent) event);
            } else if (event instanceof FileSearchSearchingEvent) {
                eventHandler.onFileSearchSearching((FileSearchSearchingEvent) event);
            } else if (event instanceof FileSearchCompletedEvent) {
                eventHandler.onFileSearchCompleted((FileSearchCompletedEvent) event);
            } else if (event instanceof WebSearchInProgressEvent) {
                eventHandler.onWebSearchInProgress((WebSearchInProgressEvent) event);
            } else if (event instanceof WebSearchSearchingEvent) {
                eventHandler.onWebSearchSearching((WebSearchSearchingEvent) event);
            } else if (event instanceof WebSearchCompletedEvent) {
                eventHandler.onWebSearchCompleted((WebSearchCompletedEvent) event);
            } else if (event instanceof CodeInterpreterInProgressEvent) {
                eventHandler.onCodeInterpreterInProgress((CodeInterpreterInProgressEvent) event);
            } else if (event instanceof CodeInterpreterInterpretingEvent) {
                eventHandler.onCodeInterpreterInterpreting((CodeInterpreterInterpretingEvent) event);
            } else if (event instanceof CodeInterpreterCompletedEvent) {
                eventHandler.onCodeInterpreterCompleted((CodeInterpreterCompletedEvent) event);
            } else if (event instanceof ReasoningTextDeltaEvent) {
                ReasoningTextDeltaEvent reasoningDelta = (ReasoningTextDeltaEvent) event;
                if (reasoningDelta.getDelta() != null) {
                    accumulatedReasoningText.append(reasoningDelta.getDelta());
                }
                eventHandler.onReasoningTextDelta(reasoningDelta);
            } else if (event instanceof ReasoningTextDoneEvent) {
                eventHandler.onReasoningTextDone((ReasoningTextDoneEvent) event);
            } else if (event instanceof ErrorEvent) {
                ErrorEvent errorEvent = (ErrorEvent) event;
                log.error("Stream error event: {}", errorEvent);
                eventHandler.onError(errorEvent);
                completed = true;
                OpenAiError openaiError = new OpenAiError();
                OpenAiError.OpenAiErrorDetails details = new OpenAiError.OpenAiErrorDetails();
                details.setMessage(errorEvent.getMessage());
                details.setCode(errorEvent.getCode());
                openaiError.setError(details);
                eventHandler.onError(new OpenAiHttpException(openaiError, null, 200));
            }
        } catch (Exception e) {
            log.error("Error handling event: {}", sse, e);
            eventHandler.onError(e);
        }
    }

    /**
     * Wait for stream completion (blocking).
     */
    public void waitForCompletion() {
        if (disposable != null && disposable.isDisposed()) {
            return;
        }
        while (!completed) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                log.error("InterruptedException while waiting for completion", e);
                shutDown();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Get the current event type.
     */
    public Optional<String> getCurrentEventType() {
        if (eventMsgsHolder.isEmpty()) {
            return Optional.empty();
        }
        ResponseSSE lastEvent = eventMsgsHolder.get(eventMsgsHolder.size() - 1);
        return Optional.ofNullable(lastEvent.getType());
    }

    /**
     * Return the SSE event stream as a new list.
     * Note: Modifying properties of internal objects will affect the original list.
     */
    public List<ResponseSSE> getEventMsgsHolder() {
        return new ArrayList<>(eventMsgsHolder);
    }
}
