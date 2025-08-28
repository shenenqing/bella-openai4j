package com.theokanning.openai.queue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a queue registration request to register an endpoint with a specific queue
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Register {
    /**
     * The name of the queue to register with
     */
    private String queue;
    
    /**
     * The endpoint to register for queue operations
     */
    private String endpoint;
}
