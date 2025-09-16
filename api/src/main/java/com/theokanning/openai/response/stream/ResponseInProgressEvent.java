package com.theokanning.openai.response.stream;

import com.theokanning.openai.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Event emitted periodically during response generation.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/response/stream">Response Stream API</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseInProgressEvent extends BaseStreamEvent {

    private String type = "response.in_progress";

    /**
     * Response object with current state.
     */
    private Response response;

    @Override
    public String getType() {
        return type;
    }
}
