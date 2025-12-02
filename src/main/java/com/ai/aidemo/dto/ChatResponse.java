package com.ai.aidemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for outgoing chat responses
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
    private String reply;
}

