package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Event emitted when a content part is done.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ContentPartDoneEvent extends BaseStreamEvent {

    /**
     * The index of the content part that is done.
     */
    @JsonProperty("content_index")
    private Integer contentIndex;

    /**
     * The ID of the output item that the content part was added to.
     */
    @JsonProperty("item_id")
    private String itemId;

    /**
     * The index of the output item that the content part was added to.
     */
    @JsonProperty("output_index")
    private Integer outputIndex;

    /**
     * The content part that is done.
     */
    private Object part;

    @Override
    public String getType() {
        return "response.content_part.done";
    }
}
