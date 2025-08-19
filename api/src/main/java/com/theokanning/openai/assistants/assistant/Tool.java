package com.theokanning.openai.assistants.assistant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author LiangTao
 * @date 2024年04月18 13:35
 **/
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
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
    String getType();

    /**
     * Function Tool
     */
    @Data
    class Function implements Tool {
        private String type = "function";
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
        private String type = "retrieval";
        @JsonProperty("default_metadata")
        private DefaultMetadata defaultMetadata;

        @Override
        public String getType() {
            return "retrieval";
        }
    }

    /**
     * RAG Tool
     */
    @Data
    class Rag implements Tool {
        private String type = "rag";
        @JsonProperty("default_metadata")
        private DefaultMetadata defaultMetadata;

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
        private String type = "web_search";

        @Override
        public String getType() {
            return "web_search";
        }
    }

    /**
     * Web Search Tavily Tool
     */
    @Data
    class WebSearchTavily implements Tool {
        private String type = "web_search_tavily";

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
        private String type = "weather_search";

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
        private String type = "img_vision";

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
        private String type = "img_generate";

        @Override
        public String getType() {
            return "img_generate";
        }
    }

    /**
     * Bar Chart Tool
     */
    @Data
    class Bar implements Tool {
        private String type = "generate_bar";

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
        private String type = "generate_line";

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
        private String type = "generate_pie";

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
        private String type = "wiki_search";

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
        private String type = "my_week_report";

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
        private String type = "week_report_to_me";

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
        private String type = "read_files";

        @Override
        public String getType() {
            return "read_files";
        }
    }

    /**
     * Web Crawler Tool
     */
    @Data
    class WebCrawler implements Tool {
        private String type = "web_crawler";

        @Override
        public String getType() {
            return "web_crawler";
        }
    }

    /**
     * Function Definition
     */
    @Data
    class FunctionDefinition {
        @NotBlank
        private String name;
        private String description;
        private Map<String, Object> parameters;
    }

    /**
     * Default Metadata for RAG/Retrieval tools
     */
    @Data
    class DefaultMetadata {
        @JsonProperty("top_k")
        private Integer topK = 3;
        private Double score = 0.8;
        @JsonProperty("empty_recall_reply")
        private String emptyRecallReply = "";
        @JsonProperty("metadata_filter")
        private List<Map<String, Object>> metadataFilter;
        @JsonProperty("retrieve_mode")
        private String retrieveMode = "fusion";
        private List<Map<String, Object>> plugins;
        private String instructions = "";
    }
}
