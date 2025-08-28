package com.theokanning.openai.queue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Represents a queue put operation for task queuing
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Put {
    /**
     * The endpoint URL for the queue operation
     */
    private String endpoint;

    /**
     * The name of the queue
     */
    private String queue;

    /**
     * The priority level of the task in the queue (default: 0)
     */
    @Builder.Default
    private Integer level = 0;

    /**
     * The data payload to be processed
     */
    private Map<String, Object> data;

    /**
     * The response mode for the operation (default: "callback") Supported modes: "callback", "blocking", "streaming"
     */
    @Builder.Default
    private String responseMode = "callback";

    /**
     * The callback URL for asynchronous responses
     */
    @JsonProperty("callback_url")
    private String callbackUrl;

    /**
     * The timeout duration for the operation in seconds
     */
    private int timeout;

    /**
     * Returns the full queue name by combining the queue name and level
     *
     * @return formatted queue name as "queueName:level"
     */
    @JsonIgnore
    public String getFullQueueName() {
        return String.format("%s:%d", queue, level);
    }
}
