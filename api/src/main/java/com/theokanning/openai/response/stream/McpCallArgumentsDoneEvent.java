package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Event emitted when the arguments for an MCP tool call are finalized.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class McpCallArgumentsDoneEvent extends BaseStreamEvent {

    /**
     * A JSON string containing the finalized arguments for the MCP tool call.
     */
    private String arguments;

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
        return "response.mcp_call_arguments.done";
    }
}
