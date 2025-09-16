package com.theokanning.openai.response.conversation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Result of deleting a conversation.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/conversation/delete">Conversation API</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteConversationResult {

    /**
     * Conversation ID that was deleted.
     */
    private String id;

    /**
     * Object type, always "conversation.deleted".
     */
    private String object = "conversation.deleted";

    /**
     * Whether the deletion was successful.
     */
    private Boolean deleted;
}
