package com.xai.services;

import com.xai.entities.AiResponse;

public interface AiService {

    String chat(String prompt);

    AiResponse getResponse(String prompt);
}
