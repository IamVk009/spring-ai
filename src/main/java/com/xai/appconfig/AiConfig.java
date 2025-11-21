package com.xai.appconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration class for setting up the {@link ChatClient} using
 * Spring AI’s {@link OpenAiChatModel}.
 *
 * <p>
 * {@code OpenAiChatModel} is the provider-specific implementation that enables
 * communication with OpenAI's LLMs. The {@code ChatClient} built from it offers
 * a higher-level, user-friendly API for sending prompts and receiving responses.
 * </p>
 *
 * <p>
 * This demonstrates the **minimum required configuration** to expose a
 * {@code ChatClient} bean and integrate OpenAI LLM capabilities into a
 * Spring Boot application.
 * </p>
 */
@Configuration
@RequiredArgsConstructor
public class AiConfig {

    /**
     * Auto-configured Spring AI model client for interacting with OpenAI LLMs.
     */
    private final OpenAiChatModel openAiChatModel;

    /**
     * Creates a {@link ChatClient} bean backed by the {@link OpenAiChatModel}.
     * This client is used across the application to perform chat operations.
     *
     * @return a configured {@code ChatClient} instance
     */
    @Bean
    public ChatClient openAiChatClient() {
        return ChatClient.builder(openAiChatModel).build();
    }
}

