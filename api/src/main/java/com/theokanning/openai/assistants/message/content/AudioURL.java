package com.theokanning.openai.assistants.message.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AudioURL {
    String url;
    @JsonProperty("audio_transcript")
    String audioTranscript;
}
