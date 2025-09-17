package com.theokanning.openai.assistants.assistant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.theokanning.openai.assistants.IUssrRequest;
import com.theokanning.openai.completion.chat.ChatResponseFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssistantRequest implements IUssrRequest {

    /**
     * ID of the model to use
     */
    @NonNull
    String model;

    /**
     * The name of the assistant. The maximum length is 256
     */
    String name;

    /**
     * The description of the assistant.The maximum length is 512 characters.
     */
    String description;

    /**
     * The system instructions that the assistant uses. The maximum length is 256,000 characters.
     */
    String instructions;

    /**
     * A list of tool enabled on the assistant.
     * There can be a maximum of 128 tools per assistant.
     * Tools can be of types code_interpreter, file_search, or function.
     */
    List<Tool> tools;


    /**
     * A set of resources that are used by the assistant's tools.
     * The resources are specific to the type of tool.
     * For example, the code_interpreter tool requires a list of file IDs, while the file_search tool requires a list of vector store IDs.
     */
    @JsonProperty("tool_resources")
    ToolResources toolResources;


    /**
     * What sampling temperature to use, between 0 and 2. Higher values like 0.8 will make the output more random, while lower values like 0.2 will make it more focused and deterministic.
     */
    Double temperature;

    /**
     * An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are considered.
     * We generally recommend altering this or temperature but not both.
     */
    @JsonProperty("top_p")
    Double topP;

    /**
     * Specifies the format that the model must output. Compatible with GPT-4 Turbo and all GPT-3.5 Turbo models since gpt-3.5-turbo-1106.
     * Setting to { "type": "json_object" } enables JSON mode, which guarantees the message the model generates is valid JSON.
     * <p>
     * Important: when using JSON mode, you must also instruct the model to produce JSON yourself via a system or user message.
     * Without this, the model may generate an unending stream of whitespace until the generation reaches the token limit, resulting in a long-running and seemingly "stuck" request.
     * Also note that the message content may be partially cut off if finish_reason="length", which indicates the generation exceeded max_tokens or the conversation exceeded the max context length.
     * <p>
     */
    @JsonProperty("response_format")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonSerialize(using = ChatResponseFormat.ChatResponseFormatSerializer.class)
    @JsonDeserialize(using = ChatResponseFormat.ChatResponseFormatDeserializer.class)

    ChatResponseFormat responseFormat;


    /**
     * Set of 16 key-value pairs that can be attached to an object.
     * This can be useful for storing additional information about the object in a structured format.
     * Keys can be a maximum of 64 characters long, and values can be a maximum of 512 characters long.
     */
    Map<String, String> metadata = new HashMap<>();

    @JsonProperty("file_ids")
    List<String> fileIds;

    @JsonProperty("reasoning_effort")
    String reasoningEffort;

    String user;

}
