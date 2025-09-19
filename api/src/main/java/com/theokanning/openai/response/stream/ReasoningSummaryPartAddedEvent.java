package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.theokanning.openai.response.content.Reasoning;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Event emitted when a new reasoning summary part is added.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ReasoningSummaryPartAddedEvent extends BaseStreamEvent {

    /**
     * The ID of the item this summary part is associated with.
     */
    @JsonProperty("item_id")
    private String itemId;

    /**
     * The index of the output item this summary part is associated with.
     */
    @JsonProperty("output_index")
    private Integer outputIndex;

    /**
     * The summary part that was added.
     */
    private Reasoning.SummaryText part;

    /**
     * The index of the summary part within the reasoning summary.
     */
    @JsonProperty("summary_index")
    private Integer summaryIndex;

    @Override
    public String getType() {
        return "response.reasoning_summary_part.added";
    }
}
