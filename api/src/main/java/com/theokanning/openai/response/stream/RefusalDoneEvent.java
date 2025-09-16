package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Event emitted when refusal text is finalized.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RefusalDoneEvent extends BaseStreamEvent {

    /**
     * The index of the content part that the refusal text is finalized.
     */
    @JsonProperty("content_index")
    private Integer contentIndex;

    /**
     * The ID of the output item that the refusal text is finalized.
     */
    @JsonProperty("item_id")
    private String itemId;

    /**
     * The index of the output item that the refusal text is finalized.
     */
    @JsonProperty("output_index")
    private Integer outputIndex;

    /**
     * The refusal text that is finalized.
     */
    private String refusal;

    @Override
    public String getType() {
        return "response.refusal.done";
    }
}