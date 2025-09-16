package com.theokanning.openai.response.stream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Event emitted when an error occurs during processing.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/response/stream">Response Stream API</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorEvent extends BaseStreamEvent {

    private String type = "error";

    /**
     * Error code.
     */
    private String code;

    /**
     * Error message.
     */
    private String message;

    /**
     * Parameter that caused the error.
     */
    private String param;

    @Override
    public String getType() {
        return type;
    }
}
