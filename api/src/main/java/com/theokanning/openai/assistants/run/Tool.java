package com.theokanning.openai.assistants.run;

import lombok.Data;

@Data
public class Tool {
    private String type = "function";
    private Function function;
}
