package com.example.CityNewsBase.service;

import com.example.CityNewsBase.api.OpenAiApiProvider.OpenAiApiProvider;
import com.example.CityNewsBase.mapper.ChatGPTResponseParser;
import com.example.CityNewsBase.model.cityNewsModel.News;
import com.example.CityNewsBase.model.newsClassification.NewsLocationClassification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenAiAnalyzerService {

    private final OpenAiApiProvider openAiApiProvider;

    public OpenAiAnalyzerService(OpenAiApiProvider openAiApiProvider) {
        this.openAiApiProvider = openAiApiProvider;
    }

    public List<NewsLocationClassification> getClassificationResultsFromChat(List<News> newsList) {
        List<NewsLocationClassification> classificationResults = new ArrayList<>();

        for (News news : newsList) {
            String classificationResult = openAiApiProvider.getResponseFromChatGPT(getNewsCityAndStatePrompt(news));
            NewsLocationClassification classification = ChatGPTResponseParser.parseClassificationResult(classificationResult);
            classificationResults.add(classification);
        }

        return classificationResults;
    }

    private String getNewsCityAndStatePrompt(News news) {
        if (news.getContent().length() < 600) return "";


        String chatQuestion = """
                Evaluate whether the following text pertains to local news or global news.
                If the text relates to local news, provide the result in the format: STATE list of states City list of cities . If the city is unknown, use City null newsScope is LOCAL.
                If the text pertains to global news, provide the news Scope as : GLOBAL and city and states are null
                If the text appears to be incorrectly scraped or nonsensical or contains any type of ads or not related content to news, return: news scope as ERROR and state and city are null
                YOU CAN ONLY USE STATES AND CITIES FROM USA if news is not about USA set news scope to global and city and state to NULL
                return text file in json format with following types
                String newsScope: {ERROR or GLOBAL or LOCAL}
                list of cities
                list of states
                EG.
                {
                  "newsScope": "LOCAL",
                  "citynames": ["Martinsville"],
                  "statenames": ["Virginia"]
                }
                dont use  ```json. Just plain text like in example.
                """;

        return news.getTitle() + " " + news.getContent() + " " + chatQuestion;
    }
}