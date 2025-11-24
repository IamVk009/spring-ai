package com.xai.appconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration class for initializing {@link ChatClient} beans
 * backed by different LLM providers (OpenAI and Ollama) using Spring AI.
 *
 * <p>
 * Each ChatClient is constructed from a specific ChatModel implementation:
 * <ul>
 *     <li>{@link OpenAiChatModel} → communicates with OpenAI’s hosted LLMs.</li>
 * </ul>
 * </p>
 *
 * <p>
 * This configuration demonstrates how to expose multiple LLM clients in the same
 * Spring Boot application with minimal code, allowing the application to select
 * or switch models as needed.
 * </p>
 */
@Configuration
@RequiredArgsConstructor
public class AiConfig {

    /**
     * Model client for interacting with OpenAI LLMs.
     */
    private final OpenAiChatModel openAiChatModel;

    /**
     * Creates a ChatClient backed by OpenAI's chat model.
     *
     * @return ChatClient instance configured for OpenAI
     */
    @Bean
    public ChatClient chatClient() {
        return ChatClient.builder(openAiChatModel).build();
    }
}


