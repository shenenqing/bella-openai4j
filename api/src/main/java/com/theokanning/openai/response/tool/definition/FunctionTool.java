package com.theokanning.openai.response.tool.definition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Function tool definition for developer-defined custom functions.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/response/create">Response API</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FunctionTool implements ToolDefinition {

    /**
     * Tool type, always "function".
     */
    private String type = "function";

    /**
     * Function name.
     */
    private String name;

    /**
     * Function description.
     */
    private String description;

    /**
     * JSON schema for function parameters.
     */
    private Object parameters;

    /**
     * Enforce strict parameter validation.
     */
    private Boolean strict;

    @Override
    public String getType() {
        return type;
    }
}

