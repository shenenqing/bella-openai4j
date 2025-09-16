package com.theokanning.openai.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Result of deleting a response.
 *
 * @see <a href="https://platform.openai.com/docs/api-reference/response/delete">Response API</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteResponseResult {

    /**
     * Response ID that was deleted.
     */
    private String id;

    /**
     * Object type, always "response".
     */
    private String object = "response";

    /**
     * Whether the deletion was successful.
     */
    private Boolean deleted;
}
