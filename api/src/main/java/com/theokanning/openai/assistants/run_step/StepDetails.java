package com.theokanning.openai.assistants.run_step;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.theokanning.openai.assistants.run.MessageCreation;
import com.theokanning.openai.assistants.run.ToolCall;
import com.theokanning.openai.response.content.Annotation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StepDetails {
    /**
     * message_creation/tool_calls
     */
    private String type;

    @JsonProperty("message_creation")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MessageCreation messageCreation;

    @JsonProperty("tool_calls")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ToolCall> toolCalls;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String text;

    // The contents of the reasoning message.
    @JsonProperty("reasoning_content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String reasoningContent;

    @JsonProperty("reasoning_content_signature")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String reasoningContentSignature;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("redacted_reasoning_content")
    private String redactedReasoningContent;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Annotation> annotations;

}
