package com.theokanning.openai.assistants.message.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContainerFileCitation {
    /**
     * Container ID.
     */
    @JsonProperty("container_id")
    private String containerId;

    /**
     * File ID.
     */
    @JsonProperty("file_id")
    private String fileId;

    /**
     * File name.
     */
    private String filename;
}
