package com.theokanning.openai.assistants.assistant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.theokanning.openai.response.tool.definition.ImageGenerationTool;
import com.theokanning.openai.response.tool.definition.LocalShellTool;
import com.theokanning.openai.response.tool.definition.ToolDefinition;
import com.theokanning.openai.response.tool.definition.WebSearchTool;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiangTao
 * @date 2024年04月18 13:35
 **/
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Tool.LocalShell.class, name = "local_shell"),
        @JsonSubTypes.Type(value = Tool.Function.class, name = "function"),
        @JsonSubTypes.Type(value = CodeInterpreterTool.class, name = "code_interpreter"),
        @JsonSubTypes.Type(value = FileSearchTool.class, name = "file_search"),
        @JsonSubTypes.Type(value = Tool.Retrieval.class, name = "retrieval"),
        @JsonSubTypes.Type(value = Tool.Rag.class, name = "rag"),
        @JsonSubTypes.Type(value = Tool.WebSearch.class, name = "web_search"),
        @JsonSubTypes.Type(value = Tool.WebSearchTavily.class, name = "web_search_tavily"),
        @JsonSubTypes.Type(value = Tool.WeatherSearch.class, name = "weather_search"),
        @JsonSubTypes.Type(value = Tool.ImgVision.class, name = "img_vision"),
        @JsonSubTypes.Type(value = Tool.ImgGenerate.class, name = "img_generate"),
        @JsonSubTypes.Type(value = Tool.Bar.class, name = "generate_bar"),
        @JsonSubTypes.Type(value = Tool.Line.class, name = "generate_line"),
        @JsonSubTypes.Type(value = Tool.Pie.class, name = "generate_pie"),
        @JsonSubTypes.Type(value = Tool.WikiSearch.class, name = "wiki_search"),
        @JsonSubTypes.Type(value = Tool.MyWeekReport.class, name = "my_week_report"),
        @JsonSubTypes.Type(value = Tool.WeekReportToMe.class, name = "week_report_to_me"),
        @JsonSubTypes.Type(value = Tool.ReadFiles.class, name = "read_files"),
        @JsonSubTypes.Type(value = Tool.WebCrawler.class, name = "web_crawler")
})
public interface Tool {
    @JsonIgnore
    String getType();

    default boolean hidden() {
        return false;
    }

    default boolean inherit() {
        return false;
    }

    default void toInherit() {}

    default ToolDefinition definition() {
        return null;
    }

    class LocalShell implements Tool {

        @Override
        public String getType() {
            return "local_shell";
        }

        @Override
        public ToolDefinition definition() {
            return new LocalShellTool();
        }
    }

    /**
     * Function Tool
     */
    @Data
    class Function implements Tool {
        private FunctionDefinition function;
        @JsonProperty("is_final")
        private Boolean isFinal = false;

        @Override
        public String getType() {
            return "function";
        }
    }

    /**
     * Retrieval Tool
     */
    @Data
    class Retrieval implements Tool {
        @JsonProperty("default_metadata")
        private DefaultMetadata defaultMetadata = new DefaultMetadata();

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Boolean hidden;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Boolean inherit;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("file_ids")
        private List<String> fileIds;

        public Retrieval() {

        }

        public Retrieval(Boolean hidden, Boolean inherit) {
            this.hidden = hidden;
            this.inherit = inherit;
        }

        @Override
        public String getType() {
            return "retrieval";
        }

        @Override
        public ToolDefinition definition() {
            com.theokanning.openai.response.tool.definition.FileSearchTool fileSearchTool = new com.theokanning.openai.response.tool.definition.FileSearchTool();
            fileSearchTool.setVectorStoreIds(fileIds);
            fileSearchTool.setRankingOptions(new com.theokanning.openai.response.tool.definition.FileSearchTool.RankingOptions(null, defaultMetadata.getScore()));
            fileSearchTool.setMaxNumResults(defaultMetadata.getTopK());
            return fileSearchTool;
        }

        @Override
        public boolean hidden() {
            return Boolean.TRUE == hidden;
        }

        @Override
        public boolean inherit() {
            return Boolean.TRUE == inherit;
        }

        @Override
        public void toInherit() {
            setInherit(true);
        }
    }

    /**
     * RAG Tool
     */
    @Data
    class Rag implements Tool {
        @JsonProperty("default_metadata")
        private DefaultMetadata defaultMetadata = new DefaultMetadata();

        @Override
        public String getType() {
            return "rag";
        }
    }

    /**
     * Web Search Tool
     */
    @Data
    class WebSearch implements Tool {

        @JsonIgnore
        private WebSearchTool definition;

        @Override
        public String getType() {
            return "web_search";
        }

        @Override
        public ToolDefinition definition() {
            return definition;
        }
    }

    /**
     * Web Search Tavily Tool
     */
    @Data
    class WebSearchTavily implements Tool {

        @Override
        public String getType() {
            return "web_search_tavily";
        }
    }

    /**
     * Weather Search Tool
     */
    @Data
    class WeatherSearch implements Tool {

        @Override
        public String getType() {
            return "weather_search";
        }
    }

    /**
     * Image Vision Tool
     */
    @Data
    class ImgVision implements Tool {

        @Override
        public String getType() {
            return "img_vision";
        }
    }

    /**
     * Image Generate Tool
     */
    @Data
    class ImgGenerate implements Tool {

        @JsonIgnore
        private ImageGenerationTool definition;

        @Override
        public String getType() {
            return "img_generate";
        }

        @Override
        public ToolDefinition definition() {
            return definition;
        }
    }

    /**
     * Bar Chart Tool
     */
    @Data
    class Bar implements Tool {

        @Override
        public String getType() {
            return "generate_bar";
        }
    }

    /**
     * Line Chart Tool
     */
    @Data
    class Line implements Tool {

        @Override
        public String getType() {
            return "generate_line";
        }
    }

    /**
     * Pie Chart Tool
     */
    @Data
    class Pie implements Tool {

        @Override
        public String getType() {
            return "generate_pie";
        }
    }

    /**
     * Wiki Search Tool
     */
    @Data
    class WikiSearch implements Tool {

        @Override
        public String getType() {
            return "wiki_search";
        }
    }

    /**
     * My Week Report Tool
     */
    @Data
    class MyWeekReport implements Tool {

        @Override
        public String getType() {
            return "my_week_report";
        }
    }

    /**
     * Week Report To Me Tool
     */
    @Data
    class WeekReportToMe implements Tool {

        @Override
        public String getType() {
            return "week_report_to_me";
        }
    }

    /**
     * Read Files Tool
     */
    @Data
    class ReadFiles implements Tool {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Boolean hidden;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Boolean inherit;

        public ReadFiles() {

        }

        public ReadFiles(Boolean hidden, Boolean inherit) {
            this.hidden = hidden;
            this.inherit = inherit;
        }

        @Override
        public String getType() {
            return "read_files";
        }

        @Override
        public boolean hidden() {
            return Boolean.TRUE == hidden;
        }

        @Override
        public boolean inherit() {
            return Boolean.TRUE == inherit;
        }

        @Override
        public void toInherit() {
            setInherit(true);
        }
    }

    /**
     * Web Crawler Tool
     */
    @Data
    class WebCrawler implements Tool {

        @Override
        public String getType() {
            return "web_crawler";
        }
    }

    /**
     * Function Definition
     */
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class FunctionDefinition {
        @NotBlank
        private String name;
        private String description;
        private Map<String, Object> parameters = new HashMap<>();
        private Boolean strict;
    }

    /**
     * Default Metadata for RAG/Retrieval tools
     */
    @Data
    class DefaultMetadata {
        @JsonProperty("top_k")
        private Integer topK = 5;
        private Double score = 0.4;
        @JsonProperty("empty_recall_reply")
        private String emptyRecallReply = "";
        @JsonProperty("metadata_filter")
        private List<Map<String, Object>> metadataFilter = new ArrayList<>();
        @JsonProperty("retrieve_mode")
        private String retrieveMode = "fusion";
        private List<Map<String, Object>> plugins = new ArrayList<>();
        private String instructions = "";
    }
}
