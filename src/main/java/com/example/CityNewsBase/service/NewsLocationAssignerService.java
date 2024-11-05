package com.example.CityNewsBase.service;

import com.example.CityNewsBase.model.cityNewsModel.City;
import com.example.CityNewsBase.model.cityNewsModel.News;
import com.example.CityNewsBase.model.cityNewsModel.State;
import com.example.CityNewsBase.model.newsClassification.NewsLocationClassification;
import com.example.CityNewsBase.model.newsClassification.NewsType;
import com.example.CityNewsBase.repository.CityRepository;
import com.example.CityNewsBase.repository.NewsRepository;
import com.example.CityNewsBase.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NewsLocationAssignerService {

    private final CityRepository cityRepository;
    private final StateRepository stateRepository;
    private final OpenAiAnalyzerService openAiAnalyzerService;

    private final NewsRepository newsRepository;

    public NewsLocationAssignerService(CityRepository cityRepository, StateRepository stateRepository, OpenAiAnalyzerService openAiAnalyzerService, NewsRepository newsRepository) {
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
        this.openAiAnalyzerService = openAiAnalyzerService;
        this.newsRepository = newsRepository;
    }

    public List<News> assignLocationsToNews(List<News> newsList) {
        List<News> newsWithLocationList = new ArrayList<>();
        List<NewsLocationClassification> classifications = openAiAnalyzerService.getClassificationResultsFromChat(newsList);


        for (int i = 0; i < newsList.size(); i++) {
            News news = newsList.get(i);
            NewsLocationClassification classification = classifications.get(i);

            if (classification.getNewsType() == NewsType.LOCAL) {
                assignCitiesAndStatesToNews(news, classification);
                news.setGlobalNews(false);
                newsWithLocationList.add(news);
            } else if (classification.getNewsType() == NewsType.GLOBAL) {
                news.setGlobalNews(true);
                newsWithLocationList.add(news);
            }


        }
        for (News news:newsWithLocationList
             ) {
            if(news.getCities() !=null){
                news.getCities().forEach(city -> System.out.println("after assign" + city.getCityName()));
            }
        }

        return newsWithLocationList;
    }

    private void assignCitiesAndStatesToNews(News news, NewsLocationClassification classification) {
        if (classification.getCityNames() != null) {
            assignCities(news, classification);
        }
        if (classification.getStateNames() != null) {
            assignStates(news, classification);
        }
    }

    private void assignStates(News news, NewsLocationClassification classification) {
        Set<State> states = new HashSet<>();

        classification.getStateNames().forEach(stateName -> stateRepository.findByNameIgnoreCase(stateName).ifPresent(states::add));

        news.setStates(states);
    }

    private void assignCities(News news, NewsLocationClassification classification) {
        Set<City> cities = new HashSet<>();

        classification.getCityNames().forEach(cityName -> classification.getStateNames().forEach(stateName -> cityRepository.findByNameAndStateIgnoreCase(cityName, stateName).ifPresent(cities::add)));

       news.setCities(cities);
        newsRepository.save(news);

    }
}