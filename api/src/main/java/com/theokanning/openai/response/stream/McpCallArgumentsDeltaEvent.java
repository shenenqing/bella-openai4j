package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Event emitted when there is a delta (partial update) to the arguments of an MCP tool call.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class McpCallArgumentsDeltaEvent extends BaseStreamEvent {

    /**
     * A JSON string containing the partial update to the arguments for the MCP tool call.
     */
    private String delta;

    /**
     * The unique identifier of the MCP tool call item being processed.
     */
    @JsonProperty("item_id")
    private String itemId;

    /**
     * The index of the output item in the response's output array.
     */
    @JsonProperty("output_index")
    private Integer outputIndex;

    @Override
    public String getType() {
        return "response.mcp_call_arguments.delta";
    }
}