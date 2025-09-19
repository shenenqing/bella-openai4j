package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Event emitted when the system is in the process of retrieving the list of available MCP tools.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class McpListToolsInProgressEvent extends BaseStreamEvent {

    /**
     * The ID of the MCP tool call item that is being processed.
     */
    @JsonProperty("item_id")
    private String itemId;

    /**
     * The index of the output item that is being processed.
     */
    @JsonProperty("output_index")
    private Integer outputIndex;

    @Override
    public String getType() {
        return "response.mcp_list_tools.in_progress";
    }
}
