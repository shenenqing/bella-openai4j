package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Event emitted when a reasoning text is completed.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ReasoningTextDoneEvent extends BaseStreamEvent {

    /**
     * The index of the reasoning content part.
     */
    @JsonProperty("content_index")
    private Integer contentIndex;

    /**
     * The ID of the item this reasoning text is associated with.
     */
    @JsonProperty("item_id")
    private String itemId;

    /**
     * The index of the output item this reasoning text is associated with.
     */
    @JsonProperty("output_index")
    private Integer outputIndex;

    /**
     * The full text of the completed reasoning content.
     */
    private String text;

    @Override
    public String getType() {
        return "response.reasoning_text.done";
    }
}