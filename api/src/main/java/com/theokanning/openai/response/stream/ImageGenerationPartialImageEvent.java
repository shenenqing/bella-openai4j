package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Event emitted when a partial image is available during image generation streaming.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ImageGenerationPartialImageEvent extends BaseStreamEvent {

    /**
     * The unique identifier of the image generation item being processed.
     */
    @JsonProperty("item_id")
    private String itemId;

    /**
     * The index of the output item in the response's output array.
     */
    @JsonProperty("output_index")
    private Integer outputIndex;

    /**
     * Base64-encoded partial image data, suitable for rendering as an image.
     */
    @JsonProperty("partial_image_b64")
    private String partialImageB64;

    /**
     * 0-based index for the partial image (backend is 1-based, but this is 0-based for the user).
     */
    @JsonProperty("partial_image_index")
    private Integer partialImageIndex;

    @Override
    public String getType() {
        return "response.image_generation_call.partial_image";
    }
}
