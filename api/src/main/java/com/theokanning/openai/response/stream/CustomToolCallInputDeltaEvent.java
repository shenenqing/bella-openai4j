package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Event representing a delta (partial update) to the input of a custom tool call.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomToolCallInputDeltaEvent extends BaseStreamEvent {

    /**
     * The incremental input data (delta) for the custom tool call.
     */
    private String delta;

    /**
     * Unique identifier for the API item associated with this event.
     */
    @JsonProperty("item_id")
    private String itemId;

    /**
     * The index of the output this delta applies to.
     */
    @JsonProperty("output_index")
    private Integer outputIndex;

    @Override
    public String getType() {
        return "response.custom_tool_call_input.delta";
    }
}