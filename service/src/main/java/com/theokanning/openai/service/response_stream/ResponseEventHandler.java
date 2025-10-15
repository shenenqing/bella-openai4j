package com.theokanning.openai.service.response_stream;

import com.theokanning.openai.response.Response;
import com.theokanning.openai.response.stream.*;

/**
 * Event handler interface for Response API streaming events.
 * Provides callback methods for different events during response generation.
 *
 * @author bella-openai4j
 */
public interface ResponseEventHandler {

    /**
     * Called for every SSE event received.
     */
    default void onEvent(ResponseSSE sse) {
    }

    /**
     * Called when a response is created.
     */
    default void onResponseCreated(ResponseCreatedEvent event) {
    }

    /**
     * Called when a response is queued.
     */
    default void onResponseQueued(ResponseQueuedEvent event) {
    }

    /**
     * Called when a response moves to in_progress status.
     */
    default void onResponseInProgress(ResponseInProgressEvent event) {
    }

    /**
     * Called when a response is completed successfully.
     */
    default void onResponseCompleted(ResponseCompletedEvent event) {
    }

    /**
     * Called when a response fails.
     */
    default void onResponseFailed(ResponseFailedEvent event) {
    }

    /**
     * Called when a response is incomplete.
     */
    default void onResponseIncomplete(ResponseIncompleteEvent event) {
    }

    /**
     * Called when a new output item is added.
     */
    default void onOutputItemAdded(OutputItemAddedEvent event) {
    }

    /**
     * Called when an output item is completed.
     */
    default void onOutputItemDone(OutputItemDoneEvent event) {
    }

    /**
     * Called when a content part is added.
     */
    default void onContentPartAdded(ContentPartAddedEvent event) {
    }

    /**
     * Called when a content part is completed.
     */
    default void onContentPartDone(ContentPartDoneEvent event) {
    }

    /**
     * Called when output text delta is received (streaming text content).
     */
    default void onOutputTextDelta(OutputTextDeltaEvent event) {
    }

    /**
     * Called when output text is completed.
     */
    default void onOutputTextDone(OutputTextDoneEvent event) {
    }

    /**
     * Called when a function call arguments delta is received.
     */
    default void onFunctionCallArgumentsDelta(FunctionCallArgumentsDeltaEvent event) {
    }

    /**
     * Called when function call arguments are completed.
     */
    default void onFunctionCallArgumentsDone(FunctionCallArgumentsDoneEvent event) {
    }

    /**
     * Called when file search call is in progress.
     */
    default void onFileSearchInProgress(FileSearchInProgressEvent event) {
    }

    /**
     * Called when file search is searching.
     */
    default void onFileSearchSearching(FileSearchSearchingEvent event) {
    }

    /**
     * Called when file search is completed.
     */
    default void onFileSearchCompleted(FileSearchCompletedEvent event) {
    }

    /**
     * Called when web search call is in progress.
     */
    default void onWebSearchInProgress(WebSearchInProgressEvent event) {
    }

    /**
     * Called when web search is searching.
     */
    default void onWebSearchSearching(WebSearchSearchingEvent event) {
    }

    /**
     * Called when web search is completed.
     */
    default void onWebSearchCompleted(WebSearchCompletedEvent event) {
    }

    /**
     * Called when code interpreter call is in progress.
     */
    default void onCodeInterpreterInProgress(CodeInterpreterInProgressEvent event) {
    }

    /**
     * Called when code interpreter is interpreting.
     */
    default void onCodeInterpreterInterpreting(CodeInterpreterInterpretingEvent event) {
    }

    /**
     * Called when code interpreter is completed.
     */
    default void onCodeInterpreterCompleted(CodeInterpreterCompletedEvent event) {
    }

    /**
     * Called when reasoning text delta is received.
     */
    default void onReasoningTextDelta(ReasoningTextDeltaEvent event) {
    }

    /**
     * Called when reasoning text is completed.
     */
    default void onReasoningTextDone(ReasoningTextDoneEvent event) {
    }

    /**
     * Called when an error occurs.
     */
    default void onError(ErrorEvent event) {
    }

    /**
     * Called when an error occurs (Throwable version).
     */
    default void onError(Throwable error) {
    }

    /**
     * Called when the stream ends.
     */
    default void onEnd() {
    }
}
