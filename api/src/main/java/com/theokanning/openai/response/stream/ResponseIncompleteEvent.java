package com.theokanning.openai.response.stream;

import com.theokanning.openai.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Event emitted when response is incomplete due to limits or constraints.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/response/stream">Response Stream API</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseIncompleteEvent extends BaseStreamEvent {

    private String type = "response.incomplete";

    /**
     * Partial response with incomplete_details.
     */
    private Response response;

    @Override
    public String getType() {
        return type;
    }
}
