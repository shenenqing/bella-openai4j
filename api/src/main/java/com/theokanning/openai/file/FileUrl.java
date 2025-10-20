package com.theokanning.openai.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * A file URL response from OpenAI
 */
@Data
public class FileUrl {

    /**
     * The URL to access the file.
     */
    String url;

    /**
     * The expiration time of the URL in epoch seconds.
     */
    @JsonProperty("expires_at")
    Long expiresAt;
}