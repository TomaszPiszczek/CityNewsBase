package com.example.CityNewsBase.webClientConfig;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WorldNewsWebClientConfig {

    @Bean
    public WebClient worldNewsWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl("https://api.worldnewsapi.com").build();
    }
}