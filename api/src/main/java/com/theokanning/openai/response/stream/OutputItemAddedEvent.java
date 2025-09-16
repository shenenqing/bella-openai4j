package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.theokanning.openai.response.ResponseItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Event emitted when new output item (message, tool call) is added.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/response/stream">Response Stream API</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutputItemAddedEvent extends BaseStreamEvent {

    private String type = "response.output_item.added";

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
        return type;
    }
}
