package com.ai.aidemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service layer for handling agent interactions.
 *
 * This service manages communication with the Gemini AI agent.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AgentService {

    private final GeminiService geminiService;

    /**
     * Send a chat message to the agent and get a response.
     *
     * @param userMessage The message from the user
     * @return The agent's response as a string
     */
    public String chat(String userMessage) {
        log.info("Processing chat request with message: {}", userMessage);

        try {
            String response = geminiService.generateContent(userMessage);
            log.info("Generated response: {}", response);
            return response;

        } catch (Exception e) {
            log.error("Error processing chat request", e);
            return "An error occurred while processing your request: " + e.getMessage();
        }
    }
}

