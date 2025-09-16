package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Event emitted when an MCP tool call has completed successfully.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class McpCallCompletedEvent extends BaseStreamEvent {

    /**
     * The ID of the MCP tool call item that completed.
     */
    @JsonProperty("item_id")
    private String itemId;

    /**
     * The index of the output item that completed.
     */
    @JsonProperty("output_index")
    private Integer outputIndex;

    @Override
    public String getType() {
        return "response.mcp_call.completed";
    }
}