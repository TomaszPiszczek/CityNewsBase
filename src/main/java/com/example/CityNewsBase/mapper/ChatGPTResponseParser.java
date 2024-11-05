package com.example.CityNewsBase.mapper;

import com.example.CityNewsBase.model.newsClassification.ClassificationResult;
import com.example.CityNewsBase.model.newsClassification.NewsLocationClassification;
import com.example.CityNewsBase.model.newsClassification.NewsType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ChatGPTResponseParser {

    public static NewsLocationClassification parseClassificationResult(String classificationResult) {

        ObjectMapper objectMapper = new ObjectMapper();
        NewsLocationClassification classification = new NewsLocationClassification();

        try {
            ClassificationResult result = objectMapper.readValue(classificationResult, ClassificationResult.class);

            if ("GLOBAL".equalsIgnoreCase(result.getNewsScope())) {
                classification.setNewsType(NewsType.GLOBAL);
            } else if (result.getCityNames().isEmpty() && result.getStateNames().isEmpty()) {
                classification.setNewsType(NewsType.ERROR);
            } else {
                classification.setNewsType(NewsType.LOCAL);
            }
            classification.setCityNames(result.getCityNames());
            classification.setStateNames(result.getStateNames());

        } catch (IOException e) {
            e.printStackTrace();
            classification.setNewsType(NewsType.ERROR);
        }

        return classification;
    }

}
