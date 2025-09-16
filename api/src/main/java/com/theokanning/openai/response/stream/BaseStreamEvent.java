package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

/**
 * Base class for all streaming events in the Response API.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/response/stream">Response Stream API</a>
 */
@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ResponseCreatedEvent.class, name = "response.created"),
        @JsonSubTypes.Type(value = ResponseInProgressEvent.class, name = "response.in_progress"),
        @JsonSubTypes.Type(value = ResponseCompletedEvent.class, name = "response.completed"),
        @JsonSubTypes.Type(value = ResponseFailedEvent.class, name = "response.failed"),
        @JsonSubTypes.Type(value = ResponseIncompleteEvent.class, name = "response.incomplete"),
        @JsonSubTypes.Type(value = ResponseQueuedEvent.class, name = "response.queued"),
        @JsonSubTypes.Type(value = OutputItemAddedEvent.class, name = "response.output_item.added"),
        @JsonSubTypes.Type(value = OutputItemDoneEvent.class, name = "response.output_item.done"),
        @JsonSubTypes.Type(value = ContentPartAddedEvent.class, name = "response.content_part.added"),
        @JsonSubTypes.Type(value = ContentPartDoneEvent.class, name = "response.content_part.done"),
        @JsonSubTypes.Type(value = OutputTextDeltaEvent.class, name = "response.output_text.delta"),
        @JsonSubTypes.Type(value = OutputTextDoneEvent.class, name = "response.output_text.done"),
        @JsonSubTypes.Type(value = OutputTextAnnotationAddedEvent.class, name = "response.output_text.annotation.added"),
        @JsonSubTypes.Type(value = RefusalDeltaEvent.class, name = "response.refusal.delta"),
        @JsonSubTypes.Type(value = RefusalDoneEvent.class, name = "response.refusal.done"),
        @JsonSubTypes.Type(value = FunctionCallArgumentsDeltaEvent.class, name = "response.function_call_arguments.delta"),
        @JsonSubTypes.Type(value = FunctionCallArgumentsDoneEvent.class, name = "response.function_call_arguments.done"),
        @JsonSubTypes.Type(value = FileSearchInProgressEvent.class, name = "response.file_search_call.in_progress"),
        @JsonSubTypes.Type(value = FileSearchSearchingEvent.class, name = "response.file_search_call.searching"),
        @JsonSubTypes.Type(value = FileSearchCompletedEvent.class, name = "response.file_search_call.completed"),
        @JsonSubTypes.Type(value = WebSearchInProgressEvent.class, name = "response.web_search_call.in_progress"),
        @JsonSubTypes.Type(value = WebSearchSearchingEvent.class, name = "response.web_search_call.searching"),
        @JsonSubTypes.Type(value = WebSearchCompletedEvent.class, name = "response.web_search_call.completed"),
        @JsonSubTypes.Type(value = CodeInterpreterInProgressEvent.class, name = "response.code_interpreter_call.in_progress"),
        @JsonSubTypes.Type(value = CodeInterpreterInterpretingEvent.class, name = "response.code_interpreter_call.interpreting"),
        @JsonSubTypes.Type(value = CodeInterpreterCompletedEvent.class, name = "response.code_interpreter_call.completed"),
        @JsonSubTypes.Type(value = CodeInterpreterCodeDeltaEvent.class, name = "response.code_interpreter_call_code.delta"),
        @JsonSubTypes.Type(value = CodeInterpreterCodeDoneEvent.class, name = "response.code_interpreter_call_code.done"),
        @JsonSubTypes.Type(value = ReasoningTextDeltaEvent.class, name = "response.reasoning_text.delta"),
        @JsonSubTypes.Type(value = ReasoningTextDoneEvent.class, name = "response.reasoning_text.done"),
        @JsonSubTypes.Type(value = ReasoningSummaryPartAddedEvent.class, name = "response.reasoning_summary_part.added"),
        @JsonSubTypes.Type(value = ReasoningSummaryPartDoneEvent.class, name = "response.reasoning_summary_part.done"),
        @JsonSubTypes.Type(value = ReasoningSummaryTextDeltaEvent.class, name = "response.reasoning_summary_text.delta"),
        @JsonSubTypes.Type(value = ReasoningSummaryTextDoneEvent.class, name = "response.reasoning_summary_text.done"),
        @JsonSubTypes.Type(value = ImageGenerationInProgressEvent.class, name = "response.image_generation_call.in_progress"),
        @JsonSubTypes.Type(value = ImageGenerationGeneratingEvent.class, name = "response.image_generation_call.generating"),
        @JsonSubTypes.Type(value = ImageGenerationCompletedEvent.class, name = "response.image_generation_call.completed"),
        @JsonSubTypes.Type(value = ImageGenerationPartialImageEvent.class, name = "response.image_generation_call.partial_image"),
        @JsonSubTypes.Type(value = McpCallArgumentsDeltaEvent.class, name = "response.mcp_call_arguments.delta"),
        @JsonSubTypes.Type(value = McpCallArgumentsDoneEvent.class, name = "response.mcp_call_arguments.done"),
        @JsonSubTypes.Type(value = McpCallInProgressEvent.class, name = "response.mcp_call.in_progress"),
        @JsonSubTypes.Type(value = McpCallCompletedEvent.class, name = "response.mcp_call.completed"),
        @JsonSubTypes.Type(value = McpCallFailedEvent.class, name = "response.mcp_call.failed"),
        @JsonSubTypes.Type(value = McpListToolsInProgressEvent.class, name = "response.mcp_list_tools.in_progress"),
        @JsonSubTypes.Type(value = McpListToolsCompletedEvent.class, name = "response.mcp_list_tools.completed"),
        @JsonSubTypes.Type(value = McpListToolsFailedEvent.class, name = "response.mcp_list_tools.failed"),
        @JsonSubTypes.Type(value = CustomToolCallInputDeltaEvent.class, name = "response.custom_tool_call_input.delta"),
        @JsonSubTypes.Type(value = CustomToolCallInputDoneEvent.class, name = "response.custom_tool_call_input.done"),
        @JsonSubTypes.Type(value = ErrorEvent.class, name = "error")
})
public abstract class BaseStreamEvent {

    /**
     * Event type identifier.
     */
    private String type;

    /**
     * Sequence number for event ordering.
     */
    @JsonProperty("sequence_number")
    private Integer sequenceNumber;

    public abstract String getType();
}
