package com.theokanning.openai.response.tool.definition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Custom tool definition with flexible input formats.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/response/create">Response API</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomTool implements ToolDefinition {

    /**
     * Tool type, always "custom".
     */
    private String type = "custom";

    /**
     * Custom tool name.
     */
    private String name;

    /**
     * Tool description.
     */
    private String description;

    /**
     * Input format specification.
     */
    private Object format; // TextFormat or GrammarFormat

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TextFormat {

        /**
         * Format type, always "text".
         */
        private String type = "text";
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GrammarFormat {

        /**
         * Format type, always "grammar".
         */
        private String type = "grammar";

        /**
         * Grammar syntax type.
         */
        private String syntax; // "lark" or "regex"

        /**
         * Grammar definition.
         */
        private String definition;
    }
}
