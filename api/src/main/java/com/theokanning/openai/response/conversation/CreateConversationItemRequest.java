package com.theokanning.openai.response.conversation;

import com.theokanning.openai.response.ConversationItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Request to add new items to a conversation.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/conversation/create-item">Conversation API</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateConversationItemRequest {

    /**
     * Items to add (max 20).
     */
    private List<ConversationItem> items = new ArrayList<>();

    /**
     * Additional fields to include in response.
     */
    private List<String> include = new ArrayList<>();
}
