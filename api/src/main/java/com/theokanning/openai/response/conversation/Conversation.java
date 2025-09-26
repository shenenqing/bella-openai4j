package com.theokanning.openai.response.conversation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a conversation for managing dialogue state and message history.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/conversation/create">Conversation API</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Conversation {

    /**
     * Conversation ID.
     */
    private String id;

    /**
     * Object type, always "conversation".
     */
    @Builder.Default
    private String object = "conversation";

    /**
     * Unix timestamp when created.
     */
    @JsonProperty("created_at")
    private Long createdAt;

    /**
     * Conversation metadata key-value pairs.
     */
    private Map<String, String> metadata = new HashMap<>();
}
