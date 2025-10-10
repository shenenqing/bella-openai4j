package com.theokanning.openai.assistants.message.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Approval {
    /**
     * The ID of the approval request being answered.
     */
    @JsonProperty("approval_request_id")
    private String approvalRequestId;

    /**
     * The label of the MCP server making the request.
     */
    @JsonProperty("server_label")
    private String serverLabel;

    /**
     * The name of the tool to run.
     */
    private String name;

    /**
     * A JSON string of arguments for the tool.
     */
    private String arguments;

    /**
     * Whether the request was approved.
     */
    private Boolean approve;

    /**
     * Optional reason for the decision.
     */
    private String reason;

    private Integer index;
}
