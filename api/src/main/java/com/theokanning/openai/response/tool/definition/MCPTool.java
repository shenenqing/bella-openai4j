package com.theokanning.openai.response.tool.definition;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * MCP (Model Context Protocol) tool definition for external system integration.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/response/create">Response API</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MCPTool implements ToolDefinition {

    /**
     * Tool type, always "mcp".
     */
    private String type = "mcp";

    /**
     * MCP server label/identifier.
     */
    @JsonProperty("server_label")
    private String serverLabel;

    /**
     * Custom MCP server URL.
     */
    @JsonProperty("server_url")
    private String serverUrl;

    /**
     * Predefined connector ID.
     */
    @JsonProperty("connector_id")
    private String connectorId;

    /**
     * OAuth authorization token.
     */
    private String authorization;

    /**
     * Custom HTTP headers.
     */
    private Map<String, String> headers;

    /**
     * Server description.
     */
    @JsonProperty("server_description")
    private String serverDescription;

    /**
     * Allowed tools configuration.
     */
    @JsonProperty("allowed_tools")
    private Object allowedTools; // List<String> or MCPToolFilter

    /**
     * Approval requirements.
     */
    @JsonProperty("require_approval")
    private Object requireApproval; // "always", "never", or MCPToolApprovalFilter

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MCPToolFilter {

        /**
         * Only allow read-only tools.
         */
        @JsonProperty("read_only")
        private Boolean readOnly;

        /**
         * Specific tool names to allow.
         */
        @JsonProperty("tool_names")
        private List<String> toolNames;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MCPToolApprovalFilter {

        /**
         * Tools that always require approval.
         */
        private MCPToolFilter always;

        /**
         * Tools that never require approval.
         */
        private MCPToolFilter never;
    }
}
