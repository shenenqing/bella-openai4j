package com.theokanning.openai.assistants.message.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AudioData {
    @JsonProperty("file_id")
    private String fileId;
    private String format;
}
