package com.theokanning.openai.response.conversation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Request to update conversation metadata.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/conversation/update">Conversation API</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateConversationRequest {

    /**
     * Updated metadata key-value pairs.
     */
    private Map<String, String> metadata;
}
