package com.theokanning.openai.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.theokanning.openai.completion.chat.ChatResponseFormat;
import com.theokanning.openai.response.content.InputImage;
import com.theokanning.openai.response.content.InputMessage;
import com.theokanning.openai.response.content.InputText;
import com.theokanning.openai.response.content.OutputMessage;
import com.theokanning.openai.response.content.OutputText;
import com.theokanning.openai.response.content.Refusal;
import com.theokanning.openai.response.content.URLCitation;
import com.theokanning.openai.response.tool.FunctionToolCall;
import com.theokanning.openai.response.tool.ImageGenerationToolCall;
import com.theokanning.openai.response.tool.definition.CustomTool;
import com.theokanning.openai.response.tool.definition.FileSearchTool;
import com.theokanning.openai.response.tool.definition.FunctionTool;
import com.theokanning.openai.response.tool.definition.ToolDefinition;
import com.theokanning.openai.response.tool.output.FunctionToolCallOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Request to create a new model response with optional tool usage and conversation context.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/response/create">Response API</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateResponseRequest {

    /**
     * Model ID to use for generation.
     */
    private String model;

    /**
     * Text, image, or file input sent to the model.
     */
    @JsonSerialize(using = InputValue.InputValueSerializer.class)
    @JsonDeserialize(using = InputValue.InputValueDeserializer.class)
    private InputValue input;

    /**
     * System/developer message providing context.
     */
    private String instructions;

    /**
     * Whether to store the response for retrieval.
     */
    private Boolean store = true;

    /**
     * Enable real-time streaming.
     */
    private Boolean stream = false;

    /**
     * Conversation this response belongs to.
     */
    @JsonSerialize(using = ConversationValue.ConversationValueSerializer.class)
    @JsonDeserialize(using = ConversationValue.ConversationValueDeserializer.class)
    private ConversationValue conversation;

    /**
     * Previous response ID for continuing conversation.
     */
    @JsonProperty("previous_response_id")
    private String previousResponseId;

    /**
     * Cache key for response optimization.
     */
    @JsonProperty("prompt_cache_key")
    private String promptCacheKey;

    /**
     * Sampling temperature (0-2).
     */
    private Double temperature = 1.0;

    /**
     * Nucleus sampling parameter.
     */
    @JsonProperty("top_p")
    private Double topP = 1.0;

    /**
     * Maximum tokens to generate.
     */
    @JsonProperty("max_output_tokens")
    private Integer maxOutputTokens;

    /**
     * Maximum number of built-in tool calls allowed.
     */
    @JsonProperty("max_tool_calls")
    private Integer maxToolCalls;

    /**
     * Text response configuration.
     */
    private TextConfig text;

    /**
     * Tools available to the model.
     */
    private List<ToolDefinition> tools;

    /**
     * Tool choice strategy.
     */
    @JsonProperty("tool_choice")
    @JsonSerialize(using = ToolChoiceValue.ToolChoiceValueSerializer.class)
    @JsonDeserialize(using = ToolChoiceValue.ToolChoiceValueDeserializer.class)
    private ToolChoiceValue toolChoice;

    /**
     * Allow parallel tool execution.
     */
    @JsonProperty("parallel_tool_calls")
    private Boolean parallelToolCalls = true;

    /**
     * Reasoning model configuration.
     */
    private ReasoningConfig reasoning;

    /**
     * Run response in background.
     */
    private Boolean background = false;

    /**
     * Context truncation strategy.
     */
    private String truncation = "disabled";

    /**
     * Additional data to include in response.
     */
    private List<String> include;

    /**
     * Response metadata key-value pairs.
     */
    private Map<String, String> metadata = new HashMap<>();

    public static void main(String[] args) throws JsonProcessingException {
        String e = "invalid type";
        String str = "{\"model\":\"o3\",\"input\":[{\"role\":\"system\",\"content\":\"你是一个智能体\"},{\"type\":\"message\",\"role\":\"user\",\"content\":\"北京现在的天气怎么样？\"},{\"type\":\"function_call\",\"name\":\"get_weather\",\"arguments\":\"{\\\"city\\\": \\\"北京\\\", \\\"unit\\\": \\\"celsius\\\"}\",\"status\":\"completed\",\"call_id\":\"call_weather456\"},{\"type\":\"function_call_output\",\"output\":\"{\\\"temperature\\\": 15, \\\"condition\\\": \\\"晴朗\\\"}\",\"status\":\"completed\",\"call_id\":\"call_weather456\"},{\"type\":\"message\",\"role\":\"user\",\"content\":\"比较一下上海的天气\"},{\"type\":\"message\",\"id\":\"msg_def456\",\"role\":\"assistant\",\"content\":[{\"type\":\"output_text\",\"text\":\"Machine learning is a subset of artificial intelligence that enables computers to learn and improve from experience.\",\"annotations\":[{\"type\":\"url_citation\",\"url\":\"https://example.com/ml-guide\",\"title\":\"Introduction to Machine Learning\",\"start_index\":0,\"end_index\":50}]}],\"status\":\"completed\"},{\"type\":\"message\",\"role\":\"user\",\"content\":[{\"type\":\"input_image\",\"image_url\":\"https://example.com/ahdhadh.jpg\"},{\"type\":\"input_text\",\"text\":\"识别图片并且生成一张相同的图片。\"}]},{\"type\":\"image_generation_call\",\"id\":\"call_cacda\",\"result\":\"https://example.com/ahdhadhadadacf.jpg\",\"status\":\"completed\"},{\"id\":\"msg_def456121313\",\"role\":\"assistant\",\"content\":[{\"type\":\"refusal\",\"refusal\":\"test\"}],\"status\":\"incomplete\"},{\"type\":\"message\",\"role\":\"user\",\"content\":\"再次生成\"}],\"store\":false,\"stream\":true,\"temperature\":1.0,\"tools\":[{\"type\":\"function\",\"name\":\"get_weather\",\"description\":\"Retrieves current weather for the given location.\",\"parameters\":{\"type\":\"object\",\"properties\":{\"location\":{\"type\":\"object\",\"properties\":{\"x\":{\"type\":\"string\"},\"y\":{\"type\":\"string\"}},\"description\":\"City and country e.g. Bogotá, Colombia\",\"required\":[\"x\",\"y\"],\"additionalProperties\":false},\"units\":{\"type\":\"string\",\"enum\":[\"celsius\",\"fahrenheit\"],\"description\":\"Units the temperature will be returned in.\"}},\"required\":[\"location\",\"units\"],\"additionalProperties\":false},\"strict\":true},{\"type\":\"file_search\"},{\"type\":\"custom\",\"name\":\"test\",\"description\":\"test\",\"format\":{\"type\":\"text\"}}],\"reasoning\":{\"effort\":\"medium\"},\"background\":false,\"truncation\":\"disabled\",\"metadata\":{},\"top_p\":1.0,\"parallel_tool_calls\":true,\"text\":{\"format\":{\"type\":\"json_schema\",\"json_schema\":{\"name\":\"analysis_result\",\"schema\":{\"type\":\"object\",\"properties\":{\"image_analysis\":{\"type\":\"string\"},\"weather_info\":{\"type\":\"object\"},\"summary\":{\"type\":\"string\"}}}}}},\"tool_choice\":{\"type\":\"allowed_tools\",\"mode\":\"auto\",\"tools\":[{\"type\":\"file_search\"},{\"type\":\"function\",\"name\":\"get_weather\"}]}}";
        ObjectMapper objectMapper = new ObjectMapper();
        CreateResponseRequest createResponseRequest = objectMapper.readValue(str, CreateResponseRequest.class);
        assertTrue(createResponseRequest.getInput().getObjectListValue().get(0) instanceof InputMessage, e);
        assertTrue(((InputMessage) createResponseRequest.getInput().getObjectListValue().get(0)).getContent().isString(), e);
        assertTrue(createResponseRequest.getInput().getObjectListValue().get(1) instanceof InputMessage, e);
        assertTrue(((InputMessage) createResponseRequest.getInput().getObjectListValue().get(1)).getContent().isString(), e);
        assertTrue(createResponseRequest.getInput().getObjectListValue().get(2) instanceof FunctionToolCall, e);
        assertTrue(createResponseRequest.getInput().getObjectListValue().get(3) instanceof FunctionToolCallOutput, e);
        assertTrue(createResponseRequest.getInput().getObjectListValue().get(4) instanceof InputMessage, e);
        assertTrue(((InputMessage) createResponseRequest.getInput().getObjectListValue().get(4)).getContent().isString(), e);
        assertTrue(createResponseRequest.getInput().getObjectListValue().get(5) instanceof OutputMessage, e);
        assertTrue(!((OutputMessage) createResponseRequest.getInput().getObjectListValue().get(5)).getContent().isString(), e);
        assertTrue(((OutputMessage) createResponseRequest.getInput().getObjectListValue().get(5)).getContent().getArrayValue()
                .get(0) instanceof OutputText, e);
        assertTrue(((OutputText) ((OutputMessage) createResponseRequest.getInput().getObjectListValue().get(5)).getContent().getArrayValue()
                .get(0)).getAnnotations().get(0) instanceof URLCitation, e);
        assertTrue(createResponseRequest.getInput().getObjectListValue().get(6) instanceof InputMessage, e);
        assertTrue(!((InputMessage) createResponseRequest.getInput().getObjectListValue().get(6)).getContent().isString(), e);
        assertTrue(((InputMessage) createResponseRequest.getInput().getObjectListValue().get(6)).getContent().getArrayValue()
                .get(0) instanceof InputImage, e);
        assertTrue(((InputMessage) createResponseRequest.getInput().getObjectListValue().get(6)).getContent().getArrayValue()
                .get(1) instanceof InputText, e);
        assertTrue(createResponseRequest.getInput().getObjectListValue().get(7) instanceof ImageGenerationToolCall, e);
        assertTrue(createResponseRequest.getInput().getObjectListValue().get(8) instanceof OutputMessage, e);
        assertTrue(!((OutputMessage) createResponseRequest.getInput().getObjectListValue().get(8)).getContent().isString(), e);
        assertTrue(
                ((OutputMessage) createResponseRequest.getInput().getObjectListValue().get(8)).getContent().getArrayValue().get(0) instanceof Refusal,
                e);
        assertTrue(createResponseRequest.getInput().getObjectListValue().get(9) instanceof InputMessage, e);
        assertTrue(((InputMessage) createResponseRequest.getInput().getObjectListValue().get(9)).getContent().isString(), e);
        assertTrue(createResponseRequest.getTools().get(0) instanceof FunctionTool, e);
        assertTrue(createResponseRequest.getTools().get(1) instanceof FileSearchTool, e);
        assertTrue(createResponseRequest.getTools().get(2) instanceof CustomTool, e);
        assertTrue(createResponseRequest.getText().getFormat().getJsonSchema() != null, e);
        assertTrue(createResponseRequest.getToolChoice().isAllowedChoice(), e);

        str = objectMapper.writeValueAsString(createResponseRequest);
        createResponseRequest = objectMapper.readValue(str, CreateResponseRequest.class);
        assertTrue(createResponseRequest.getInput().getObjectListValue().get(0) instanceof InputMessage, e);
        assertTrue(((InputMessage) createResponseRequest.getInput().getObjectListValue().get(0)).getContent().isString(), e);
        assertTrue(createResponseRequest.getInput().getObjectListValue().get(1) instanceof InputMessage, e);
        assertTrue(((InputMessage) createResponseRequest.getInput().getObjectListValue().get(1)).getContent().isString(), e);
        assertTrue(createResponseRequest.getInput().getObjectListValue().get(2) instanceof FunctionToolCall, e);
        assertTrue(createResponseRequest.getInput().getObjectListValue().get(3) instanceof FunctionToolCallOutput, e);
        assertTrue(createResponseRequest.getInput().getObjectListValue().get(4) instanceof InputMessage, e);
        assertTrue(((InputMessage) createResponseRequest.getInput().getObjectListValue().get(4)).getContent().isString(), e);
        assertTrue(createResponseRequest.getInput().getObjectListValue().get(5) instanceof OutputMessage, e);
        assertTrue(!((OutputMessage) createResponseRequest.getInput().getObjectListValue().get(5)).getContent().isString(), e);
        assertTrue(((OutputMessage) createResponseRequest.getInput().getObjectListValue().get(5)).getContent().getArrayValue()
                .get(0) instanceof OutputText, e);
        assertTrue(((OutputText) ((OutputMessage) createResponseRequest.getInput().getObjectListValue().get(5)).getContent().getArrayValue()
                .get(0)).getAnnotations().get(0) instanceof URLCitation, e);
        assertTrue(createResponseRequest.getInput().getObjectListValue().get(6) instanceof InputMessage, e);
        assertTrue(!((InputMessage) createResponseRequest.getInput().getObjectListValue().get(6)).getContent().isString(), e);
        assertTrue(((InputMessage) createResponseRequest.getInput().getObjectListValue().get(6)).getContent().getArrayValue()
                .get(0) instanceof InputImage, e);
        assertTrue(((InputMessage) createResponseRequest.getInput().getObjectListValue().get(6)).getContent().getArrayValue()
                .get(1) instanceof InputText, e);
        assertTrue(createResponseRequest.getInput().getObjectListValue().get(7) instanceof ImageGenerationToolCall, e);
        assertTrue(createResponseRequest.getInput().getObjectListValue().get(8) instanceof OutputMessage, e);
        assertTrue(!((OutputMessage) createResponseRequest.getInput().getObjectListValue().get(8)).getContent().isString(), e);
        assertTrue(
                ((OutputMessage) createResponseRequest.getInput().getObjectListValue().get(8)).getContent().getArrayValue().get(0) instanceof Refusal,
                e);
        assertTrue(createResponseRequest.getInput().getObjectListValue().get(9) instanceof InputMessage, e);
        assertTrue(((InputMessage) createResponseRequest.getInput().getObjectListValue().get(9)).getContent().isString(), e);
        assertTrue(createResponseRequest.getTools().get(0) instanceof FunctionTool, e);
        assertTrue(createResponseRequest.getTools().get(1) instanceof FileSearchTool, e);
        assertTrue(createResponseRequest.getTools().get(2) instanceof CustomTool, e);
        assertTrue(createResponseRequest.getText().getFormat().getJsonSchema() != null, e);
        assertTrue(createResponseRequest.getToolChoice().isAllowedChoice(), e);

        str = objectMapper.writeValueAsString(createResponseRequest);
        System.out.println(str);
    }

    private static void assertTrue(boolean condition, String message) {
        if(!condition) {
            throw new AssertionError(message);
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TextConfig {
        /**
         * Response format (text, json_schema, json_object).
         */
        @JsonSerialize(using = ChatResponseFormat.ChatResponseFormatSerializer.class)
        @JsonDeserialize(using = ChatResponseFormat.ChatResponseFormatDeserializer.class)
        private ChatResponseFormat format;

        /**
         * Response verbosity level.
         */
        private String verbosity;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ReasoningConfig {
        /**
         * Reasoning effort level.
         */
        private String effort;

        /**
         * Reasoning summary level.
         */
        private String summary;
    }

}
