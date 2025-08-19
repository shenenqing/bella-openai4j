package com.theokanning.openai.assistants.assistant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class FunctionResources {
    private String name;
    @JsonProperty("file_ids")
    private List<String> fileIds = Collections.emptyList();
}
