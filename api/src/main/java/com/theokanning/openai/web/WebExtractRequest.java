package com.theokanning.openai.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.theokanning.openai.assistants.IUssrRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * Web Extract Request based on Tavily Extract API
 * Extract web page content from one or more specified URLs using Tavily Extract
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class WebExtractRequest implements IUssrRequest, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * The URLs to extract content from (required)
     * Example: ["https://en.wikipedia.org/wiki/Artificial_intelligence"]
     */
    @NotEmpty(message = "URLs cannot be empty")
    private List<String> urls;

    /**
     * Model to use for the extract request
     */
    private String model;

    /**
     * A unique identifier representing your end-user
     */
    private String user;

    /**
     * Include a list of images extracted from the URLs in the response
     * Default: false
     */
    @JsonProperty("include_images")
    private Boolean includeImages = false;

    /**
     * Whether to include the favicon URL for each result
     * Default: false
     */
    @JsonProperty("include_favicon")
    private Boolean includeFavicon = false;

    /**
     * The depth of the extraction process
     * - basic: costs 1 credit per 5 successful URL extractions
     * - advanced: costs 2 credits per 5 successful URL extractions
     * Default: basic
     */
    @JsonProperty("extract_depth")
    private ExtractDepth extractDepth = ExtractDepth.BASIC;

    /**
     * The format of the extracted web page content
     * - markdown: returns content in markdown format
     * - text: returns plain text and may increase latency
     * Default: markdown
     */
    private Format format = Format.MARKDOWN;

    /**
     * Maximum time in seconds to wait for the URL extraction before timing out
     * Must be between 1.0 and 60.0 seconds
     * If not specified, default timeouts are applied based on extract_depth:
     * - 10 seconds for basic extraction
     * - 30 seconds for advanced extraction
     */
    private Double timeout;

    /**
     * Extract depth enum for extraction complexity
     */
    @AllArgsConstructor
    public enum ExtractDepth {
        BASIC("basic"),
        ADVANCED("advanced");

        private final String value;

        @JsonValue
        public String getValue() {
            return value;
        }
    }

    /**
     * Format enum for content extraction format
     */
    @AllArgsConstructor
    public enum Format {
        MARKDOWN("markdown"),
        TEXT("text");

        private final String value;

        @JsonValue
        public String getValue() {
            return value;
        }
    }
}
