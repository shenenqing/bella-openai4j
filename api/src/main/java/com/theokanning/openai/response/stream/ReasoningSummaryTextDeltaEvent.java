package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Event emitted for reasoning summary updates.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/response/stream">Response Stream API</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReasoningSummaryTextDeltaEvent extends BaseStreamEvent {

    private String type = "response.reasoning_summary_text.delta";

    /**
     * Item ID.
     */
    @JsonProperty("item_id")
    private String itemId;

    /**
     * Output index.
     */
    @JsonProperty("output_index")
    private Integer outputIndex;

    /**
     * Summary index.
     */
    @JsonProperty("summary_index")
    private Integer summaryIndex;

    /**
     * Summary text chunk.
     */
    private String delta;

    @Override
    public String getType() {
        return type;
    }
}
