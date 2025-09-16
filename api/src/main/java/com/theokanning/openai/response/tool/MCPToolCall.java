package com.theokanning.openai.response.tool;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * An invocation of a tool on an MCP server.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/response/create">Response API</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MCPToolCall extends ToolCall {

    /**
     * The type of the item. Always "mcp_call".
     */
    private String type = "mcp_call";

    /**
     * The unique ID of the tool call.
     */
    private String id;

    /**
     * The name of the tool that was run.
     */
    private String name;

    /**
     * The label of the MCP server running the tool.
     */
    @JsonProperty("server_label")
    private String serverLabel;

    /**
     * A JSON string of the arguments passed to the tool.
     */
    private String arguments;

    /**
     * The output from the tool call.
     */
    private String output;

    /**
     * The error from the tool call, if any.
     */
    private String error;

    @Override
    public String getType() {
        return type;
    }
}
