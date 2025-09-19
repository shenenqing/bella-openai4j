package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.theokanning.openai.response.ResponseItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Event emitted when new output item (message, tool call) is added.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/response/stream">Response Stream API</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OutputItemAddedEvent extends BaseStreamEvent {


    /**
     * Position in the output array.
     */
    @JsonProperty("output_index")
    private Integer outputIndex;

    /**
     * The output item (OutputMessage or ToolCall).
     */
    private ResponseItem item;

    @Override
    public String getType() {
        return "response.output_item.added";
    }
}
