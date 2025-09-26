package com.theokanning.openai.response.conversation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.theokanning.openai.response.ConversationItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * List of conversation items with pagination support.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/conversation/list-items">Conversation API</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationItemList {

    /**
     * Object type, always "list".
     */
    @Builder.Default
    private String object = "list";

    /**
     * Array of conversation items (InputMessage, OutputMessage, ToolCall).
     */
    private List<ConversationItem> data = new ArrayList<>();

    /**
     * First item ID.
     */
    @JsonProperty("first_id")
    private String firstId;

    /**
     * Last item ID.
     */
    @JsonProperty("last_id")
    private String lastId;

    /**
     * Whether there are more items available.
     */
    @JsonProperty("has_more")
    private Boolean hasMore;
}
