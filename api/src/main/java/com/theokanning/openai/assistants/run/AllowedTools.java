package com.theokanning.openai.assistants.run;

import lombok.Data;

import java.util.List;

@Data
public class AllowedTools {
    String mode;
    List<Tool> tools;
}
