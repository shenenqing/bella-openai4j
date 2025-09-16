package com.theokanning.openai.response.content;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.theokanning.openai.response.ItemStatus;
import com.theokanning.openai.response.MessageRole;

import java.io.IOException;

/**
 * Custom deserializer for messages that chooses between InputMessage and OutputMessage based on the role field.
 */
public class MessageDeserializer extends JsonDeserializer<ContentMessage> {

    @Override
    public ContentMessage deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.readValueAsTree();

        // Check the role to determine message type
        JsonNode roleNode = node.get("role");
        if(roleNode != null) {
            String role = roleNode.asText();

            // Create the appropriate message instance and populate fields manually
            if("assistant".equals(role)) {
                OutputMessage message = new OutputMessage();
                populateMessage(message, node, parser, context);
                return message;
            } else {
                InputMessage message = new InputMessage();
                populateMessage(message, node, parser, context);
                return message;
            }
        }

        throw new IOException("Cannot determine message type - missing role field");
    }

    private void populateMessage(ContentMessage message, JsonNode node, JsonParser originalParser, DeserializationContext context)
            throws IOException {
        // Set basic fields
        if(node.has("role")) {
            message.setRole(MessageRole.valueOf(node.get("role").asText().toUpperCase()));
        }

        // Handle type-specific fields
        if(message instanceof InputMessage) {
            InputMessage inputMsg = (InputMessage) message;
            if(node.has("content")) {
                // Parse content using InputContentValue deserializer
                JsonParser contentParser = node.get("content").traverse(originalParser.getCodec());
                contentParser.nextToken();
                InputContentValue.InputContentValueDeserializer deserializer = new InputContentValue.InputContentValueDeserializer();
                InputContentValue content = deserializer.deserialize(contentParser, context);
                inputMsg.setContent(content);
            }
        } else if(message instanceof OutputMessage) {
            OutputMessage outputMsg = (OutputMessage) message;
            if(node.has("content")) {
                // Parse content using OutputContentValue deserializer
                JsonParser contentParser = node.get("content").traverse(originalParser.getCodec());
                contentParser.nextToken();
                OutputContentValue.OutputContentValueDeserializer deserializer = new OutputContentValue.OutputContentValueDeserializer();
                OutputContentValue content = deserializer.deserialize(contentParser, context);
                outputMsg.setContent(content);
            }
            if(node.has("id")) {
                outputMsg.setId(node.get("id").asText());
            }
            if(node.has("status")) {
                outputMsg.setStatus(ItemStatus.valueOf(node.get("status").asText().toUpperCase()));
            }
        }
    }
}
