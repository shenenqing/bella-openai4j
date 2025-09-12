package com.theokanning.openai.completion.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssistantMultipleMessage implements ChatMessage {
    final String role = ChatMessageRole.ASSISTANT.value();

    // The contents of the assistant message. Required unless tool_calls or function_call is specified.
    @JsonDeserialize(using = ContentDeserializer.class)
    @JsonSerialize(using = ContentSerializer.class)
    private Object content;

    // The contents of the reasoning message.
    @JsonProperty("reasoning_content")
    String reasoningContent;

    @JsonProperty("reasoning_content_signature")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String reasoningContentSignature;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("redacted_reasoning_content")
    private String redactedReasoningContent;

    //An optional name for the participant. Provides the model information to differentiate between participants of the same role.
    String name;

    @JsonProperty("tool_calls")
    List<ChatToolCall> toolCalls;

    /**
     * @deprecated Replaced by tool_calls
     */
    @Deprecated
    @JsonProperty("function_call")
    ChatFunctionCall functionCall;

    /**
     * when response_format is json_schema, the assistant can return a refusal message.
     */
    private String refusal;

    /**
     * Data about a previous audio response from the model.
     */
    private AssistantMessageAudio audio;


    public AssistantMultipleMessage(Object content) {
        this.content = content;
    }

    public AssistantMultipleMessage(Object content, String name) {
        this.content = content;
        this.name = name;
    }

    @Override
    @JsonIgnore
    public String getTextContent() {
        if (content instanceof String) {
            return (String) content;
        }
        if (content instanceof Collection) {
            Collection collection = (Collection) content;
            Optional<String> text = collection.stream().filter(item -> item instanceof MultiMediaContent)
                    .filter(imageContent -> ((MultiMediaContent) imageContent).getType().equals("text"))
                    .findFirst().map(imageContent -> ((MultiMediaContent) imageContent).getText());
            if (text.isPresent()) {
                return text.get();
            }
        }
        return null;
    }
    
}
