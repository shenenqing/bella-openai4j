package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Event emitted when a reasoning summary text is completed.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ReasoningSummaryTextDoneEvent extends BaseStreamEvent {

    /**
     * The ID of the item this summary text is associated with.
     */
    @JsonProperty("item_id")
    private String itemId;

    /**
     * The index of the output item this summary text is associated with.
     */
    @JsonProperty("output_index")
    private Integer outputIndex;

    /**
     * The index of the summary part within the reasoning summary.
     */
    @JsonProperty("summary_index")
    private Integer summaryIndex;

    /**
     * The full text of the completed reasoning summary.
     */
    private String text;

    @Override
    public String getType() {
        return "response.reasoning_summary_text.done";
    }
}