package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Event emitted when the attempt to list available MCP tools has failed.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class McpListToolsFailedEvent extends BaseStreamEvent {

    /**
     * The ID of the MCP tool call item that failed.
     */
    @JsonProperty("item_id")
    private String itemId;

    /**
     * The index of the output item that failed.
     */
    @JsonProperty("output_index")
    private Integer outputIndex;

    @Override
    public String getType() {
        return "response.mcp_list_tools.failed";
    }
}