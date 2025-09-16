package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.theokanning.openai.response.ResponseItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Event emitted when an output item is completed.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/response/stream">Response Stream API</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutputItemDoneEvent extends BaseStreamEvent {

    private String type = "response.output_item.done";

    /**
     * Output index.
     */
    @JsonProperty("output_index")
    private Integer outputIndex;

    /**
     * Completed item.
     */
    private ResponseItem item;

    @Override
    public String getType() {
        return type;
    }
}
