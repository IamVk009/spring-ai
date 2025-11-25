package com.xai.controllers;

import com.xai.entities.AiResponse;
import com.xai.services.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller exposing endpoints for interacting with LLM providers
 * through Spring AI’s {@link ChatClient}.
 *
 * <p>This controller demonstrates how a Spring Boot application can use
 * Spring AI to communicate with large language models in a clean,
 * provider-agnostic manner. Although multiple LLM providers can be configured
 * in the application, this controller exposes an endpoint backed by the
 * OpenAI-powered {@link ChatClient} via {@link AiService}.</p>
 *
 * <h2>LLM Provider Overview</h2>
 * <ul>
 *     <li><strong>OpenAI</strong> – accessed through the configured
 *     {@link ChatClient} which uses {@code OpenAiChatModel} underneath.</li>
 *     <li><strong>Ollama</strong> – also supported by the application
 *     (if configured), enabling requests to local or self-hosted models.</li>
 * </ul>
 *
 * <p>
 * This controller stays intentionally minimal and delegates model selection and
 * execution logic to {@link AiService}, keeping the REST layer lightweight and
 * focused on request/response handling.
 * </p>
 */
@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class AiController {

    /**
     * ChatClient configured to communicate with OpenAI's chat models.
     * Injected through constructor-based dependency injection.
     */
    private final ChatClient chatClient;

    /**
     * Service layer responsible for interacting with the LLMs.
     */
    private final AiService aiService;

    /**
     * Sends a prompt to the OpenAI-backed {@link ChatClient}.
     *
     * @param prompt the user-provided input text
     * @return the LLM-generated response wrapped in {@link ResponseEntity}
     */
    @GetMapping
    public ResponseEntity<String> askOpenAi(@RequestParam String prompt) {
        return ResponseEntity.ok(aiService.chat(prompt));
    }

    /**
     * Handles HTTP GET requests for generating an AI response based on the provided prompt.
     *
     * <p>This endpoint accepts a text prompt as a query parameter, forwards it to the
     * {@code aiService} for processing, and returns the generated {@link AiResponse}
     * wrapped in a {@link ResponseEntity}. The AI service is responsible for invoking
     * the underlying AI model and mapping the response.</p>
     *
     * <p>Example request:</p>
     * <pre>
     * GET /response?prompt=Hello
     * </pre>
     *
     * @param prompt the text prompt to send to the AI model; must not be {@code null}
     * @return a {@link ResponseEntity} containing the generated {@link AiResponse}
     */
    @GetMapping("/response")
    public ResponseEntity<AiResponse> getResponse(String prompt) {
        return ResponseEntity.ok(aiService.getResponse(prompt));
    }

    /**
     * Returns a list of AI responses generated for the given prompt.
     *
     * @param prompt the text prompt to send to the AI model
     * @return a list of {@link AiResponse} wrapped in a {@link ResponseEntity}
     */
    @GetMapping("/responses")
    public ResponseEntity<List<AiResponse>> getResponseList(String prompt) {
        return ResponseEntity.ok(aiService.getResponseList(prompt));
    }
}
