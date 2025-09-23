package com.theokanning.openai.response.tool;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.theokanning.openai.response.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * An image generation request made by the model.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/response/create">Response API</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageGenerationToolCall extends ToolCall {

    /**
     * The type of the image generation call. Always "image_generation_call".
     */
    private String type = "image_generation_call";

    /**
     * The unique ID of the image generation call.
     */
    private String id;

    /**
     * The generated image encoded in base64 / url.
     */
    private String result;

    /**
     * The status of the image generation call.
     */
    private ItemStatus status;

    /**
     * base64 / url
     */
    @JsonProperty("data_type")
    private String dataType;

    @Override
    public String getType() {
        return type;
    }
}
