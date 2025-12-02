package com.ai.aidemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for incoming chat requests
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {
    private String message;
}

