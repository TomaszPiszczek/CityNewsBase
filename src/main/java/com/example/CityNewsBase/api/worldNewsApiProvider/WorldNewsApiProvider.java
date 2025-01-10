package com.example.CityNewsBase.api.worldNewsApiProvider;

import com.example.CityNewsBase.model.worldNewsModel.WorldNews;
import com.example.CityNewsBase.model.worldNewsModel.WorldNewsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component

public class WorldNewsApiProvider {

    private final WebClient worldNewsWebClient;

    public WorldNewsApiProvider(@Qualifier("worldNewsWebClient") WebClient worldNewsWebClient) {
        this.worldNewsWebClient = worldNewsWebClient;
    }


    @Value("${newsapi.api-key}")
    private String newsApiKey;


    public List<WorldNews> getNews(String earliestPublishDate, int offset) {


        String language = "en";
        String sourceCountry = "US";
        String url = String.format("/search-news?api-key=%s&language=%s&source-country=%s&earliest-publish-date=%s&offset=%d",
                newsApiKey, language, sourceCountry, earliestPublishDate, offset);

        WorldNewsResponse response = worldNewsWebClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(WorldNewsResponse.class)
                .block();

            return response.news();
    }


}
