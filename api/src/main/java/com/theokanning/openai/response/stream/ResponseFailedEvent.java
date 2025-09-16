package com.theokanning.openai.response.stream;

import com.theokanning.openai.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Event emitted when response generation fails.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/response/stream">Response Stream API</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseFailedEvent extends BaseStreamEvent {

    private String type = "response.failed";

    /**
     * Response with error details.
     */
    private Response response;

    @Override
    public String getType() {
        return type;
    }
}
