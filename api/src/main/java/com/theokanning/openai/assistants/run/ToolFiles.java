package com.theokanning.openai.assistants.run;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ToolFiles {
    //{"toolName":["file_id1", "file_id2"]}
    Map<String, List<String>> tools;
}
