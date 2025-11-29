package com.xai.services.impl;

import com.xai.entities.AiResponse;
import com.xai.services.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiServiceImpl implements AiService {

    private final ChatClient chatClient;

    @Override
    public String chat(String prompt) {

        /**
         * Sends a prompt to the chat client and retrieves the content response.
         *
         * This method uses a predefined prompt and fetches the content from the chat client.
         * It assumes that `prompt` is already initialized and configured.
         *
         * @return The content returned by the chat client after processing the prompt.
         */
/*
        var content = chatClient
                .prompt(prompt)  // Passes the prompt to the chat client.
                .call()  // Executes the request to the chat client.
                .content();  // Retrieves the content from the response.

*/

        /**
         * Sends a user-specific prompt and a system instruction to the chat client,
         * requesting an expert-level response.
         *
         * This method sets both a user prompt and a system message, instructing the
         * chat client to act as an expert in Java. The response content is then fetched.
         */
/*
        var content1 = chatClient
                .prompt()  // Creates a new prompt instance.
                .user("Tell me about Collections Framework in Java")  // Sets the user message for the prompt.
                .system("Act as an Expert in Java")  // Sets the system message instructing the chat client.
                .call()  // Executes the request to the chat client.
                .content();  // Retrieves the content from the response.
*/

        /**
         * Sends a custom user-defined prompt to the chat client and retrieves the content.
         * This method creates a new `Prompt` object based on the provided prompt and
         * sends it to the chat client. The response content is retrieved after the request is executed.
         *
         * @param prompt The custom prompt to be sent to the chat client.
         * @return The content returned by the chat client after processing the prompt.
         */
/*
        Prompt userPrompt = new Prompt(prompt);  // Initializes a new Prompt object with the given prompt.
        var content2 = chatClient
                .prompt(userPrompt)  // Passes the custom user-defined prompt to the chat client.
                .call()  // Executes the request to the chat client.
                .content();  // Retrieves the content from the response.
*/

//        Get Metadata of the OpenAI API call
        ChatResponseMetadata metadata = chatClient
                .prompt(prompt)
                .call()
                .chatResponse()
                .getMetadata();
        log.info("metadata: {}", metadata);

//        Another way to get the Response.
        var response = chatClient.prompt(prompt)
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getText();

        return response;
    }

    /**
     * Generates an AI response for the given prompt by invoking the chat client.
     *
     * <p>This method sends the provided prompt to the underlying {@code chatClient},
     * executes the request, and maps the returned response into an {@link AiResponse}
     * object. The {@code chatClient} is expected to handle the communication with the
     * AI model (e.g., OpenAI or other LLM provider).</p>
     *
     * @param prompt the user input or instruction to be processed by the AI model;
     *               must not be {@code null}
     * @return an {@link AiResponse} mapped from the model's output
     * @throws RuntimeException if the chat client call fails or the response cannot be mapped
     */
    @Override
    public AiResponse getResponse(String prompt) {
        return chatClient
                .prompt(prompt)
                .call()
                .entity(AiResponse.class);
    }

    /**
     * Generates a list of AI responses for the given prompt by invoking the chat client.
     *
     * <p>This method sends the provided prompt to the underlying {@code chatClient},
     * executes the request, and maps the returned JSON array into a {@code List<AiResponse>}.
     * Because Java uses type erasure for generics, a {@link ParameterizedTypeReference}
     * is required to preserve the full generic type information at runtime. Without it,
     * the JSON mapper would only see a raw {@code List} and would be unable to correctly
     * deserialize the elements into {@link AiResponse} objects.</p>
     *
     *
     * A {@link ParameterizedTypeReference} is used to preserve generic type information
     * at runtime so that the JSON mapper can correctly deserialize complex generic types.
     *
     * <p>Because Java uses <em>type erasure</em>, generic types such as
     * {@code List<AiResponse>} lose their actual type information at runtime and become
     * simply {@code List}. This prevents deserialization frameworks (e.g., Jackson,
     * Spring's HTTP converters) from knowing the concrete element type that should be
     * created during mapping.</p>
     *
     * <p>Providing a {@code new ParameterizedTypeReference<List<AiResponse>>() {}}}
     * captures the full generic type (including the parameter {@code AiResponse}) and
     * makes it available to the deserializer. This ensures that the framework can
     * correctly convert a JSON array into a strongly typed {@code List<AiResponse>}.</p>
     *
     * <p>In summary, {@code ParameterizedTypeReference} is required whenever you need to
     * deserialize parameterized types (e.g., lists, maps, nested generics) so the runtime
     * can retain the full type information that would otherwise be erased.</p>
     *
     *  @param prompt the text prompt to send to the AI model; must not be {@code null}
     *  @return a list of {@link AiResponse} objects generated by the AI model
    */
    @Override
    public List<AiResponse> getResponseList(String prompt) {
        return chatClient
                .prompt(prompt)
                .call()
                .entity(new ParameterizedTypeReference<List<AiResponse>>() {
                });
    }

    /**
     * Generates a response from the LLM using prompt-specific generation parameters.
     * <p>
     * This method builds a {@link Prompt} with its own ChatOptions such as model,
     * temperature, max tokens, frequency penalty, presence penalty, and top-p.
     * These options apply only to this specific prompt invocation.
     * </p>
     *
     * <p><b>Important:</b> Since the generation parameters are defined inside the prompt,
     * they cannot be reused or shared across different parts of the application.
     * If the same configuration is needed in multiple places, define global/default
     * ChatOptions in the Spring AI configuration instead of setting them here.</p>
     *
     * @param message the user input or query to send to the LLM
     * @return the generated response text from the model
     */
    @Override
    public String getResponseUsingPromptDefaults(String message) {
        Prompt prompt = Prompt.builder()
                .chatOptions(ChatOptions.builder()
                        .model("gpt-4o")            // The specific LLM to use for generating responses
                        .maxTokens(200)             // Maximum length of the generated reply (limits output size)
                        .temperature(0.5)           // Controls creativity (lower = factual, higher = creative)
                        .frequencyPenalty(0.2)      // Reduces repeated words or phrases in the response
                        .presencePenalty(0.1)       // Encourages the model to introduce new ideas or topics
                        .topP(1.0)
                        .build())
                .content(message)
                .build();
        return chatClient.prompt(prompt).call().content();
    }

    /**
     * Generates a response from the LLM using a predefined prompt template.
     * <p>
     * This method performs the following steps:
     * <ol>
     *   <li>Creates a prompt template containing placeholders.</li>
     *   <li>Renders the template by injecting actual values for the placeholders.</li>
     *   <li>Constructs a prompt with the rendered text.</li>
     *   <li>Calls the LLM using the chat client and returns its response.</li>
     * </ol>
     *
     * @return The generated content returned by the LLM.
     */
    @Override
    public String getResponseUsingPromptTemplate() {
//          Step 1: Create a Prompt Template
        PromptTemplate promptTemplate = PromptTemplate.builder()
                .template("Explain briefly about {sport} in 100 words, and also provide a short summary of {playerName}.")
                .build();

//          Step 2: Render the template with actual values
        String renderedMessage = promptTemplate.render(Map.of(
                "sport", "Football",
                "playerName", "Harry Kane"
        ));

//         Step 3: Create the final Prompt using the rendered message
        Prompt prompt = new Prompt(renderedMessage);

//        Step 4: Call the LLM using chatClient
        return chatClient.prompt(prompt).call().content();
    }

    /**
     * Generates a response from the LLM using both a system prompt template and a user prompt template.
     * <p>
     * This method:
     * <ol>
     *   <li>Creates a system prompt that defines the assistant's role (football expert).</li>
     *   <li>Renders a user prompt template with dynamic values such as sport and player name.</li>
     *   <li>Combines both prompts into a single {@link Prompt}.</li>
     *   <li>Invokes the LLM through the chat client and returns the generated content.</li>
     * </ol>
     *
     * @return The response generated by the LLM based on both system and user prompt templates.
     */
    @Override
    public String getResponseUsingSystemAndUserPromptTemplate() {
        SystemPromptTemplate systemPromptTemplate = SystemPromptTemplate.builder()
                .template("You are a world-class football expert who provides clear, accurate, and insightful facts.")
                .build();

        Message systemMessage = systemPromptTemplate.createMessage();

        PromptTemplate userPromptTemplate = PromptTemplate.builder()
                .template("Explain briefly about {sport} in 100 words, and also provide a short summary of {playerName}.")
                .build();

        Message userMessage = userPromptTemplate.createMessage(Map.of(
                "sport", "football",
                "playerName", "Wayne Roney"
        ));

        Prompt prompt = new Prompt(systemMessage, userMessage);
        return chatClient.prompt(prompt).call().content();
    }
}
