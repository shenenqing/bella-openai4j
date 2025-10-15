package com.theokanning.openai.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.theokanning.openai.assistants.IUssrRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Web Search Request based on Tavily Search API Provides unified access to web search functionality with comprehensive configuration options
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class WebSearchRequest implements IUssrRequest, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * The search query to execute with Tavily (required) Example: "who is Leo Messi?"
     */
    private String query;

    /**
     * Model to use for the search request
     */
    private String model;

    /**
     * A unique identifier representing your end-user
     */
    private String user;

    /**
     * When auto_parameters is enabled, Tavily automatically configures search parameters based on your query's content and intent. Default: false
     */
    @JsonProperty("auto_parameters")
    private Boolean autoParameters = false;

    /**
     * The category of the search. Default: general - news: useful for retrieving real-time updates - general: for broader, more general-purpose
     * searches - finance: for financial information
     */
    private Topic topic = Topic.GENERAL;

    /**
     * The depth of the search. Default: basic - basic: provides generic content snippets (1 API Credit) - advanced: retrieves most relevant sources
     * and content snippets (2 API Credits)
     */
    @JsonProperty("search_depth")
    private SearchDepth searchDepth = SearchDepth.BASIC;

    /**
     * Chunks are short content snippets (maximum 500 characters each) pulled directly from the source. Maximum number of relevant chunks returned per
     * source. Default: 3 Available only when search_depth is advanced.
     */
    @JsonProperty("chunks_per_source")
    private Integer chunksPerSource;

    /**
     * The maximum number of search results to return. Default: 5
     */
    @JsonProperty("max_results")
    private Integer maxResults;

    /**
     * The time range back from the current date to filter results (publish date) Useful when looking for sources that have published data
     */
    @JsonProperty("time_range")
    private TimeRange timeRange;

    /**
     * Number of days back from the current date to include (publish date) Available only if topic is news. Default: 7
     */
    private Integer days;

    /**
     * Will return all results after the specified start date (publish date) Required to be written in the format YYYY-MM-DD Example: "2025-02-09"
     */
    @JsonProperty("start_date")
    private String startDate;

    /**
     * Will return all results before the specified end date (publish date) Required to be written in the format YYYY-MM-DD Example: "2000-01-28"
     */
    @JsonProperty("end_date")
    private String endDate;

    /**
     * Include the cleaned and parsed HTML content of each search result. Default: false - markdown or true: returns search result content in markdown
     * format - text: returns the plain text from the results and may increase latency
     */
    @JsonProperty("include_raw_content")
    private Boolean includeRawContent = false;

    /**
     * Also perform an image search and include the results in the response. Default: false
     */
    @JsonProperty("include_images")
    private Boolean includeImages = false;

    /**
     * When include_images is true, also add a descriptive text for each image. Default: false
     */
    @JsonProperty("include_image_descriptions")
    private Boolean includeImageDescriptions = false;

    /**
     * Whether to include the favicon URL for each result. Default: false
     */
    @JsonProperty("include_favicon")
    private Boolean includeFavicon = false;

    /**
     * A list of domains to specifically include in the search results Maximum 300 domains
     */
    @JsonProperty("include_domains")
    private List<String> includeDomains;

    /**
     * A list of domains to specifically exclude from the search results Maximum 150 domains
     */
    @JsonProperty("exclude_domains")
    private List<String> excludeDomains;

    /**
     * Boost search results from a specific country. This will prioritize content from the selected country in the search results. Available only if
     * topic is general.
     */
    private String country;

    /**
     * Topic enum for search category
     */
    @Getter
    @AllArgsConstructor
    public enum Topic {
        GENERAL("general"),
        NEWS("news"),
        FINANCE("finance");

        private final String value;

        @JsonValue
        public String getValue() {
            return value;
        }
    }

    /**
     * Search depth enum
     */
    @Getter
    @AllArgsConstructor
    public enum SearchDepth {
        BASIC("basic"),
        ADVANCED("advanced");

        private final String value;

        @JsonValue
        public String getValue() {
            return value;
        }
    }

    /**
     * Time range enum for filtering results by publish date
     */
    @Getter
    @AllArgsConstructor
    public enum TimeRange {
        DAY("day"),
        WEEK("week"),
        MONTH("month"),
        YEAR("year"),
        D("d"),
        W("w"),
        M("m"),
        Y("y");

        private final String value;

        @JsonValue
        public String getValue() {
            return value;
        }
    }
}
