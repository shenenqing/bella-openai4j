package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Event emitted when an MCP tool call has failed.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class McpCallFailedEvent extends BaseStreamEvent {

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
        return "response.mcp_call.failed";
    }
}
