package com.theokanning.openai.queue;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Event bus configuration for queue message publishing and subscription
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventbusConfig {

    /**
     * Event bus url
     */
    private String url;

    /**
     * Topic name for message routing and subscription
     */
    private String topic;

}
