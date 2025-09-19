package com.theokanning.openai.assistants.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.theokanning.openai.assistants.message.content.ImageFile;
import com.theokanning.openai.assistants.message.content.Text;
import com.theokanning.openai.completion.chat.ChatToolCall;
import com.theokanning.openai.completion.chat.ImageUrl;
import com.theokanning.openai.completion.chat.ToolMessage;
import com.theokanning.openai.response.tool.ToolCall;
import lombok.Data;


/**
 * Represents the content of a message
 * <p>
 * https://platform.openai.com/docs/api-reference/messages/object
 */
@Data
public class MessageContent {

    /**
     * image_url/image_file/text/tool_call/tool_result
     */
    String type;

    /**
     * The text content that is part of a message.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Text text;

    /**
     * References an image File in the content of a message.
     */
    @JsonProperty("image_file")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    ImageFile imageFile;


    /**
     * References an Image URL in the content of a message.
     */
    @JsonProperty("image_url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    ImageUrl imageUrl;

    @JsonProperty("tool_call")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    ChatToolCall toolCall;

    @JsonProperty("tool_result")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    ToolMessage toolResult;

    public boolean empty() {
        switch (type) {
        case "image_file":
            return imageFile == null;
        case "image_url":
            return imageUrl == null;
        case "tool_call":
            return toolCall == null;
        case "toolResult":
            return toolResult == null;
        default:
            return text == null || text.getValue() == null || text.getValue().trim().isEmpty();
        }
    }

    public boolean isVision() {
        return type.equals("image_url") || type.equals("image_file");
    }
}
