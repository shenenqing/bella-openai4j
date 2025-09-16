package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Event emitted when an annotation is added to output text content.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OutputTextAnnotationAddedEvent extends BaseStreamEvent {

    /**
     * The annotation object being added. (See annotation schema for details.)
     */
    private Object annotation;

    /**
     * The index of the annotation within the content part.
     */
    @JsonProperty("annotation_index")
    private Integer annotationIndex;

    /**
     * The index of the content part within the output item.
     */
    @JsonProperty("content_index")
    private Integer contentIndex;

    /**
     * The unique identifier of the item to which the annotation is being added.
     */
    @JsonProperty("item_id")
    private String itemId;

    /**
     * The index of the output item in the response's output array.
     */
    @JsonProperty("output_index")
    private Integer outputIndex;

    @Override
    public String getType() {
        return "response.output_text.annotation.added";
    }
}