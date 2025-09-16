package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Event emitted for incremental reasoning content (reasoning models only).
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/response/stream">Response Stream API</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReasoningTextDeltaEvent extends BaseStreamEvent {

    private String type = "response.reasoning_text.delta";

    /**
     * Item ID.
     */
    @JsonProperty("item_id")
    private String itemId;

    /**
     * Output index.
     */
    @JsonProperty("output_index")
    private Integer outputIndex;

    /**
     * Content index.
     */
    @JsonProperty("content_index")
    private Integer contentIndex;

    /**
     * Reasoning text chunk.
     */
    private String delta;

    @Override
    public String getType() {
        return type;
    }
}
