package com.xai.services.impl;

import com.xai.services.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final ChatClient chatClient;

    @Override
    public String chat(String prompt) {
        return chatClient.prompt(prompt).call().content();
    }
}
