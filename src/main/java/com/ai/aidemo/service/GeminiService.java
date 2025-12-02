package com.ai.aidemo.service;

import com.ai.aidemo.agent.HelloTimeAgent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

/**
 * Service for interacting with Google's Gemini API.
 *
 * This service handles:
 * - Building requests with function calling support
 * - Calling the Gemini API
 * - Processing responses and handling function calls
 */
@Service
@Slf4j
public class GeminiService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final String apiKey;

    private static final String GEMINI_API_BASE = "https://generativelanguage.googleapis.com/v1beta";
    private static final String MODEL_NAME = "gemini-2.0-flash-exp";

    public GeminiService(
            @Value("${google.api.key:${GOOGLE_API_KEY:}}") String apiKey,
            ObjectMapper objectMapper) {

        if (apiKey == null || apiKey.trim().isEmpty()) {
            log.error("GOOGLE_API_KEY is not set!");
            throw new IllegalStateException("GOOGLE_API_KEY is required");
        }

        this.apiKey = apiKey;
        this.objectMapper = objectMapper;
        this.webClient = WebClient.builder()
                .baseUrl(GEMINI_API_BASE)
                .build();

        log.info("GeminiService initialized successfully");
    }

    /**
     * Generate content using Gemini with function calling support.
     */
    public String generateContent(String userMessage) {
        try {
            // Build the request with function declarations
            ObjectNode request = buildRequest(userMessage);

            // Call Gemini API
            String response = callGeminiApi(request);

            // Process the response and handle function calls
            return processResponse(response);

        } catch (WebClientResponseException e) {
            log.error("Gemini API error: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            return "I apologize, but I encountered an error: " + e.getMessage();
        } catch (Exception e) {
            log.error("Error generating content", e);
            return "I apologize, but I encountered an error: " + e.getMessage();
        }
    }

    /**
     * Build the API request with function declarations.
     */
    private ObjectNode buildRequest(String userMessage) {
        ObjectNode request = objectMapper.createObjectNode();

        // Add contents
        ArrayNode contents = request.putArray("contents");
        ObjectNode content = contents.addObject();
        content.put("role", "user");
        ArrayNode parts = content.putArray("parts");
        parts.addObject().put("text", userMessage);

        // Add function declarations (tools)
        ArrayNode tools = request.putArray("tools");
        ObjectNode tool = tools.addObject();
        ArrayNode functionDeclarations = tool.putArray("functionDeclarations");

        // Declare getCurrentTime function
        ObjectNode functionDecl = functionDeclarations.addObject();
        functionDecl.put("name", "getCurrentTime");
        functionDecl.put("description", "Get the current time in a specified city");

        ObjectNode parameters = functionDecl.putObject("parameters");
        parameters.put("type", "object");

        ObjectNode properties = parameters.putObject("properties");
        ObjectNode cityParam = properties.putObject("city");
        cityParam.put("type", "string");
        cityParam.put("description", "The name of the city");

        ArrayNode required = parameters.putArray("required");
        required.add("city");

        return request;
    }

    /**
     * Call the Gemini API.
     */
    private String callGeminiApi(ObjectNode request) {
        String endpoint = String.format("/models/%s:generateContent?key=%s", MODEL_NAME, apiKey);

        return webClient.post()
                .uri(endpoint)
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    /**
     * Process the API response and handle function calls.
     */
    private String processResponse(String response) throws Exception {
        JsonNode root = objectMapper.readTree(response);

        // Check for candidates
        JsonNode candidates = root.path("candidates");
        if (candidates.isEmpty()) {
            return "I couldn't process your request.";
        }

        JsonNode candidate = candidates.get(0);
        JsonNode content = candidate.path("content");
        JsonNode parts = content.path("parts");

        if (parts.isEmpty()) {
            return "I couldn't generate a response.";
        }

        // Check if there's a function call
        JsonNode firstPart = parts.get(0);
        if (firstPart.has("functionCall")) {
            return handleFunctionCall(firstPart.path("functionCall"));
        }

        // Otherwise, extract text response
        if (firstPart.has("text")) {
            return firstPart.path("text").asText();
        }

        return "I couldn't generate a proper response.";
    }

    /**
     * Handle function calls from the API response.
     */
    private String handleFunctionCall(JsonNode functionCall) {
        String functionName = functionCall.path("name").asText();
        JsonNode args = functionCall.path("args");

        log.info("Function call: {} with args: {}", functionName, args);

        if ("getCurrentTime".equals(functionName)) {
            String city = args.path("city").asText();
            Map<String, String> result = HelloTimeAgent.getCurrentTime(city);

            // Format the response
            return formatTimeResponse(city, result);
        }

        return "I couldn't execute the function: " + functionName;
    }

    /**
     * Format the time response in a user-friendly way.
     */
    private String formatTimeResponse(String city, Map<String, String> timeInfo) {
        String time = timeInfo.get("time");
        String timezone = timeInfo.get("timezone");

        if ("Unknown".equals(timezone)) {
            return String.format("I'm sorry, but I couldn't determine the timezone for %s.", city);
        }

        return String.format("The current time in %s is %s (%s).",
                city, time, timezone);
    }
}

