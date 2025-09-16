package com.theokanning.openai.response.conversation;

import com.theokanning.openai.response.ConversationItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Request to create a new conversation with optional initial items.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/conversation/create">Conversation API</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateConversationRequest {

    /**
     * Initial conversation items (max 20).
     */
    private List<ConversationItem> items = new ArrayList<>();

    /**
     * Conversation metadata key-value pairs.
     */
    private Map<String, String> metadata = new HashMap<>();
}
