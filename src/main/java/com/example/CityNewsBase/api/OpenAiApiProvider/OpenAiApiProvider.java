package com.example.CityNewsBase.api.OpenAiApiProvider;

import com.example.CityNewsBase.model.openAiModel.request.OpenAiRequest;
import com.example.CityNewsBase.model.openAiModel.response.OpenAiResponse;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Component
public class OpenAiApiProvider {
    private final WebClient openAiWebClient;

    public OpenAiApiProvider(@Qualifier("openAiWebClient") WebClient openAiWebClient) {
        this.openAiWebClient = openAiWebClient;
    }

    public String getResponseFromChatGPT(String prompt) {
        if(prompt.equals("")){
            return """
                    {
                         "newsScope": "ERROR",
                         "citynames": [],
                          "statenames": []
                    }
                    """;
        }

        return openAiWebClient.post()
                .body(Mono.just(new OpenAiRequest(prompt)), OpenAiRequest.class)
                .retrieve()
                .bodyToMono(OpenAiResponse.class)
                .map(responseJson -> responseJson.getChoices().get(0).getMessage().getContent())
                .block();
    }
}
