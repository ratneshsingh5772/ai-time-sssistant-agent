package com.ai.aidemo.controller;

import com.ai.aidemo.dto.ChatRequest;
import com.ai.aidemo.dto.ChatResponse;
import com.ai.aidemo.service.AgentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for handling chat interactions with the AI agent.
 *
 * Endpoints:
 * - POST /api/chat - Send a message to the agent
 * - GET /api/chat/health - Health check
 *
 * How to test:
 *
 * 1. Make sure GOOGLE_API_KEY is set:
 *    export GOOGLE_API_KEY=your-api-key-here
 *
 * 2. Run the application:
 *    mvn spring-boot:run
 *
 * 3. Test with curl:
 *    curl -X POST http://localhost:8080/api/chat \
 *         -H "Content-Type: application/json" \
 *         -d '{"message": "What is the time in Delhi?"}'
 *
 * 4. Other example queries:
 *    curl -X POST http://localhost:8080/api/chat \
 *         -H "Content-Type: application/json" \
 *         -d '{"message": "What is the time in New York?"}'
 *
 *    curl -X POST http://localhost:8080/api/chat \
 *         -H "Content-Type: application/json" \
 *         -d '{"message": "Tell me the current time in Tokyo and London"}'
 */
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
public class AgentController {

    private final AgentService agentService;

    /**
     * Main chat endpoint.
     *
     * @param request The chat request containing the user's message
     * @return The agent's response
     */
    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        log.info("Received chat request: {}", request.getMessage());

        if (request.getMessage() == null || request.getMessage().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ChatResponse("Message cannot be empty"));
        }

        try {
            String reply = agentService.chat(request.getMessage());
            return ResponseEntity.ok(new ChatResponse(reply));
        } catch (Exception e) {
            log.error("Error processing chat request", e);
            return ResponseEntity.internalServerError()
                    .body(new ChatResponse("An error occurred: " + e.getMessage()));
        }
    }

    /**
     * Health check endpoint.
     *
     * @return Simple status message
     */
    @GetMapping("/health")
    public ResponseEntity<ChatResponse> health() {
        return ResponseEntity.ok(new ChatResponse("Agent service is running"));
    }
}

