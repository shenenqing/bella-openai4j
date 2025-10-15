package com.theokanning.openai.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.theokanning.openai.assistants.IUssrRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * Web Crawl Request based on Tavily Crawl API Provides comprehensive web crawling functionality with configurable depth and filtering options
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class WebCrawlRequest implements IUssrRequest, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * The root URL to begin the crawl (required) Example: "docs.tavily.com"
     */
    @NotBlank(message = "URL cannot be blank")
    private String url;

    /**
     * Model to use for the crawl request
     */
    private String model;

    /**
     * A unique identifier representing your end-user
     */
    private String user;

    /**
     * Natural language instructions for the crawler When specified, the mapping cost increases to 2 API credits per 10 successful pages instead of 1
     * API credit per 10 pages Example: "Find all pages about the Python SDK"
     */
    private String instructions;

    /**
     * Max depth of the crawl. Defines how far from the base URL the crawler can explore Default: 1
     */
    @JsonProperty("max_depth")
    private Integer maxDepth = 1;

    /**
     * Max number of links to follow per level of the tree (i.e., per page) Default: 20
     */
    @JsonProperty("max_breadth")
    private Integer maxBreadth = 20;

    /**
     * Total number of links the crawler will process before stopping Default: 50
     */
    private Integer limit = 50;

    /**
     * Regex patterns to select only URLs with specific path patterns Example: ["/docs/.*", "/api/v1.*"]
     */
    @JsonProperty("select_paths")
    private List<String> selectPaths;

    /**
     * Regex patterns to select crawling to specific domains or subdomains Example: ["^docs\\.example\\.com$"]
     */
    @JsonProperty("select_domains")
    private List<String> selectDomains;

    /**
     * Regex patterns to exclude URLs with specific path patterns Example: ["/private/.*", "/admin/.*"]
     */
    @JsonProperty("exclude_paths")
    private List<String> excludePaths;

    /**
     * Regex patterns to exclude specific domains or subdomains from crawling Example: ["^private\\.example\\.com$"]
     */
    @JsonProperty("exclude_domains")
    private List<String> excludeDomains;

    /**
     * Whether to include external domain links in the final results list Default: true
     */
    @JsonProperty("allow_external")
    private Boolean allowExternal = true;

    /**
     * Whether to include images in the crawl results Default: false
     */
    @JsonProperty("include_images")
    private Boolean includeImages = false;

    /**
     * Advanced extraction retrieves more data, including tables and embedded content, with higher success but may increase latency - basic: costs 1
     * credit per 5 successful extractions - advanced: costs 2 credits per 5 successful extractions Default: basic
     */
    @JsonProperty("extract_depth")
    private ExtractDepth extractDepth = ExtractDepth.BASIC;

    /**
     * The format of the extracted web page content - markdown: returns content in markdown format - text: returns plain text and may increase latency
     * Default: markdown
     */
    private Format format = Format.MARKDOWN;

    /**
     * Whether to include the favicon URL for each result Default: false
     */
    @JsonProperty("include_favicon")
    private Boolean includeFavicon = false;

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
