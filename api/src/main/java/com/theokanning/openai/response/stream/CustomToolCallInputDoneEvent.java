package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Event indicating that input for a custom tool call is complete.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomToolCallInputDoneEvent extends BaseStreamEvent {

    /**
     * The complete input data for the custom tool call.
     */
    private String input;

    /**
     * Unique identifier for the API item associated with this event.
     */
    @JsonProperty("item_id")
    private String itemId;

    /**
     * The index of the output this event applies to.
     */
    @JsonProperty("output_index")
    private Integer outputIndex;

    @Override
    public String getType() {
        return "response.custom_tool_call_input.done";
    }
}