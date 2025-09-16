package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Event emitted when there is a partial refusal text.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RefusalDeltaEvent extends BaseStreamEvent {

    /**
     * The index of the content part that the refusal text is added to.
     */
    @JsonProperty("content_index")
    private Integer contentIndex;

    /**
     * The refusal text that is added.
     */
    private String delta;

    /**
     * The ID of the output item that the refusal text is added to.
     */
    @JsonProperty("item_id")
    private String itemId;

    /**
     * The index of the output item that the refusal text is added to.
     */
    @JsonProperty("output_index")
    private Integer outputIndex;

    @Override
    public String getType() {
        return "response.refusal.delta";
    }
}