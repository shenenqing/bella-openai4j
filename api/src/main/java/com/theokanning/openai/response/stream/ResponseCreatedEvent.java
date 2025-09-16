package com.theokanning.openai.response.stream;

import com.theokanning.openai.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Event emitted when a response is created and begins processing.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/response/stream">Response Stream API</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCreatedEvent extends BaseStreamEvent {

    private String type = "response.created";

    /**
     * Complete response object.
     */
    private Response response;

    @Override
    public String getType() {
        return type;
    }
}
