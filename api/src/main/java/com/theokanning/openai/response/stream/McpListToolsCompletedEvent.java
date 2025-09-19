package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Event emitted when the list of available MCP tools has been successfully retrieved.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class McpListToolsCompletedEvent extends BaseStreamEvent {

    /**
     * The ID of the MCP tool call item that produced this output.
     */
    @JsonProperty("item_id")
    private String itemId;

    /**
     * The index of the output item that was processed.
     */
    @JsonProperty("output_index")
    private Integer outputIndex;

    @Override
    public String getType() {
        return "response.mcp_list_tools.completed";
    }
}
