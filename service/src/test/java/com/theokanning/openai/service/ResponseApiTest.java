package com.theokanning.openai.service;

import com.theokanning.openai.response.ConversationValue;
import com.theokanning.openai.response.CreateResponseRequest;
import com.theokanning.openai.response.InputValue;
import com.theokanning.openai.response.Response;
import com.theokanning.openai.response.ResponseStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Response API Test - Non-streaming mode
 * Tests basic Response API operations without streaming.
 *
 * @author bella-openai4j
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResponseApiTest {

    static OpenAiService service;
    static String responseId;

    @BeforeAll
    static void setup() {
        // Use longer timeout for Response API
        service = new OpenAiService(Duration.ofSeconds(60));
    }

    @Test
    @Order(1)
    @DisplayName("Create response - simple text input")
    void testCreateResponseSimple() {
        CreateResponseRequest request = CreateResponseRequest.builder()
                .model("gpt-4o-mini")
                .input(InputValue.of("Say 'Hello, World!' in response"))
                .build();

        Response response = service.createResponse(request);

        assertNotNull(response);
        assertNotNull(response.getId());
        responseId = response.getId();
        assertEquals("response", response.getObject());
        assertNotNull(response.getStatus());
        assertNotNull(response.getOutput());
        assertFalse(response.getOutput().isEmpty());

        System.out.println("Response ID: " + response.getId());
        System.out.println("Status: " + response.getStatus());
        System.out.println("Output items: " + response.getOutput().size());
    }

    @Test
    @Order(2)
    @DisplayName("Create response - with instructions")
    void testCreateResponseWithInstructions() {
        CreateResponseRequest request = CreateResponseRequest.builder()
                .model("gpt-4o-mini")
                .input(InputValue.of("What is 2+2?"))
                .instructions("You are a helpful math teacher. Be concise.")
                .temperature(0.7)
                .maxOutputTokens(100)
                .build();

        Response response = service.createResponse(request);

        assertNotNull(response);
        assertEquals(ResponseStatus.COMPLETED, response.getStatus());
        assertNotNull(response.getInstructions());

        System.out.println("Response with instructions completed");
    }

    @Test
    @Order(3)
    @DisplayName("Retrieve response by ID")
    void testGetResponse() {
        if (responseId == null) {
            System.out.println("Skipping: No response ID available");
            return;
        }

        Response response = service.getResponse(responseId);

        assertNotNull(response);
        assertEquals(responseId, response.getId());
        assertEquals("response", response.getObject());

        System.out.println("Retrieved response: " + response.getId());
    }

    @Test
    @Order(4)
    @DisplayName("Create response - with conversation")
    void testCreateResponseWithConversation() {
        // Create first response
        CreateResponseRequest request1 = CreateResponseRequest.builder()
                .model("gpt-4o-mini")
                .input(InputValue.of("My name is Alice"))
                .conversation(ConversationValue.of("test-conversation-1"))
                .build();

        Response response1 = service.createResponse(request1);
        assertNotNull(response1);

        // Create second response in same conversation
        CreateResponseRequest request2 = CreateResponseRequest.builder()
                .model("gpt-4o-mini")
                .input(InputValue.of("What is my name?"))
                .conversation(ConversationValue.of("test-conversation-1"))
                .previousResponseId(response1.getId())
                .build();

        Response response2 = service.createResponse(request2);
        assertNotNull(response2);
        assertEquals(response1.getId(), response2.getPreviousResponseId());

        System.out.println("Conversation test completed");
    }
}
