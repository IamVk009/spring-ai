package com.xai.services;

import com.xai.entities.AiResponse;

import java.util.List;

public interface AiService {

    String chat(String prompt);

    AiResponse getResponse(String prompt);

    List<AiResponse> getResponseList(String prompt);

    String getResponseUsingPromptDefaults(String prompt);

    String getResponseUsingPromptTemplate();

    String getResponseUsingSystemAndUserPromptTemplate();

    String getResponseUsingFluentApi();

    String getResponseByFetchingPromptFromExternalFiles();
}
