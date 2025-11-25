package com.xai.services.impl;

import com.xai.services.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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
        var content = chatClient
                .prompt(prompt)  // Passes the prompt to the chat client.
                .call()  // Executes the request to the chat client.
                .content();  // Retrieves the content from the response.


        /**
         * Sends a user-specific prompt and a system instruction to the chat client,
         * requesting an expert-level response.
         *
         * This method sets both a user prompt and a system message, instructing the
         * chat client to act as an expert in Java. The response content is then fetched.
         */
        var content1 = chatClient
                .prompt()  // Creates a new prompt instance.
                .user("Tell me about Collections Framework in Java")  // Sets the user message for the prompt.
                .system("Act as an Expert in Java")  // Sets the system message instructing the chat client.
                .call()  // Executes the request to the chat client.
                .content();  // Retrieves the content from the response.

        /**
         * Sends a custom user-defined prompt to the chat client and retrieves the content.
         * This method creates a new `Prompt` object based on the provided prompt and
         * sends it to the chat client. The response content is retrieved after the request is executed.
         *
         * @param prompt The custom prompt to be sent to the chat client.
         * @return The content returned by the chat client after processing the prompt.
         */
        Prompt userPrompt = new Prompt(prompt);  // Initializes a new Prompt object with the given prompt.
        var content2 = chatClient
                .prompt(userPrompt)  // Passes the custom user-defined prompt to the chat client.
                .call()  // Executes the request to the chat client.
                .content();  // Retrieves the content from the response.

        return content2;
    }
}
