package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Event emitted when an MCP tool call is in progress.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class McpCallInProgressEvent extends BaseStreamEvent {

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
        return "response.mcp_call.in_progress";
    }
}
