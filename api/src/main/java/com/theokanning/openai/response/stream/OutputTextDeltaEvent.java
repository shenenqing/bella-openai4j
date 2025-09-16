package com.theokanning.openai.response.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Event emitted for incremental text updates during generation.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/response/stream">Response Stream API</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutputTextDeltaEvent extends BaseStreamEvent {

    private String type = "response.output_text.delta";

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
     * Content index.
     */
    @JsonProperty("content_index")
    private Integer contentIndex;

    /**
     * New text chunk.
     */
    private String delta;

    /**
     * Optional log probabilities.
     */
    private List<LogProb> logprobs;

    @Override
    public String getType() {
        return type;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LogProb {
        private double logprob;
        private String token;
        @JsonProperty("top_logprobs")
        private List<TopLogProb> topLogprobs;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopLogProb {
        private double logprob;
        private String token;
    }
}
