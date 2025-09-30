package com.theokanning.openai.assistants.message.content;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class URLCitation {
    /**
     * Web page URL.
     */
    private String url;

    /**
     * Web page title.
     */
    private String title;
}
