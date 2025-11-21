package com.xai.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes a simple endpoint to interact with an OpenAI LLM
 * using Spring AI’s {@link OpenAiChatModel}.
 *
 * <p>
 * {@code OpenAiChatModel} is the Spring AI client that handles communication
 * with OpenAI’s chat models. It takes care of authentication, request formatting,
 * and response handling internally.
 * </p>
 *
 * <p>
 * This example demonstrates the **minimum required code** to integrate an LLM
 * into a Spring Boot application using Spring AI.
 * </p>
 */
@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class AiController {

    /**
     * Auto-configured Spring AI client for interacting with OpenAI chat models.
     */
    private final ChatClient.Builder chatClientBuilder;

    /**
     * Sends the user prompt to the OpenAI model and returns its response.
     *
     * @param prompt the text input from the user
     * @return the generated response from the LLM
     */
    @GetMapping
    public ResponseEntity<String> askOpenAi(@RequestParam String prompt) {
        return ResponseEntity.ok(this.chatClientBuilder.build().prompt(prompt).call().content());
    }
}