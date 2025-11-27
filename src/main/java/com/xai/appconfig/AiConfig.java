package com.xai.appconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
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
     * Creates and configures the application-wide {@link ChatClient} bean backed by
     * the provided {@link OpenAiChatModel}.
     *
     * <p>This client defines global/default {@link ChatOptions} such as model,
     * temperature, token limits, and penalties. These defaults are applied to every
     * prompt executed through this {@link ChatClient} unless explicitly overridden
     * in individual prompt calls.</p>
     *
     * <p><b>Note:</b> Because the options are set at the client level, they are
     * shared across the entire application. If different endpoints or services
     * require different LLM settings, they must override these defaults per prompt.</p>
     *
     * @return a fully configured {@link ChatClient} instance with default LLM options
     */
    @Bean
    public ChatClient chatClient() {
        return ChatClient.builder(openAiChatModel)
                .defaultOptions(ChatOptions.builder()
                        .model("gpt-4o")            // The specific LLM to use for generating responses
                        .maxTokens(300)             // Maximum length of the generated reply (limits output size)
                        .temperature(0.5)           // Controls creativity (lower = factual, higher = creative)
                        .frequencyPenalty(0.2)      // Reduces repeated words or phrases in the response
                        .presencePenalty(0.1)       // Encourages the model to introduce new ideas or topics
                        .topP(1.0)
                        .build())
                .build();
    }
}



