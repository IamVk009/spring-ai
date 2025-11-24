package com.xai.appconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration for creating {@link ChatClient} beans backed by
 * OpenAI's LLMs via Spring AI.
 *
 * <p>This configuration provides a single {@link ChatClient} instance that
 * delegates to an {@link OpenAiChatModel}, allowing the application to
 * interact with OpenAI-powered chat models through Spring AI's abstraction.</p>
 *
 * <h2>Overview</h2>
 * <ul>
 *     <li>{@link OpenAiChatModel} – the underlying model implementation used
 *     to communicate with OpenAI’s LLM APIs.</li>
 *     <li>{@link ChatClient} – a high-level Spring AI client used to send
 *     prompts and receive responses.</li>
 * </ul>
 *
 * <p>By exposing the {@link ChatClient} as a Spring bean, it can be injected
 * wherever conversational AI functionality is required within the application.</p>
 */
@Configuration
@RequiredArgsConstructor
public class AiConfig {

    /**
     * The model used to communicate with OpenAI’s chat-based LLM endpoints.
     */
    private final OpenAiChatModel openAiChatModel;

    /**
     * Creates a {@link ChatClient} bean backed by the configured
     * {@link OpenAiChatModel}.
     *
     * <p>This client provides a convenient abstraction for sending prompts to
     * OpenAI and handling responses within Spring-based components.</p>
     *
     * @return a configured {@link ChatClient} instance
     */
    @Bean
    public ChatClient chatClient() {
        return ChatClient.builder(openAiChatModel).build();
    }
}



