package com.example.CityNewsBase.service;

import com.example.CityNewsBase.api.worldNewsApiProvider.WorldNewsApiProvider;
import com.example.CityNewsBase.mapper.NewsMapper;
import com.example.CityNewsBase.model.cityNewsModel.City;
import com.example.CityNewsBase.model.cityNewsModel.News;
import com.example.CityNewsBase.model.cityNewsModel.State;
import com.example.CityNewsBase.model.worldNewsModel.WorldNews;
import com.example.CityNewsBase.repository.NewsRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class NewsService {

    private final WorldNewsApiProvider worldNewsApiProvider;
    private final NewsLocationAssignerService newsLocationAssignerService;
    private final NewsRepository newsRepository;
    private final EntityManager entityManager;

    public NewsService(WorldNewsApiProvider worldNewsApiProvider, NewsLocationAssignerService newsLocationAssignerService, NewsRepository newsRepository, EntityManager entityManager) {
        this.worldNewsApiProvider = worldNewsApiProvider;
        this.newsLocationAssignerService = newsLocationAssignerService;
        this.newsRepository = newsRepository;
        this.entityManager = entityManager;
    }

    public List<News> getNewsWithLocation(String earliestPublishDate, int offset) {
        List<WorldNews> newsFromApi = worldNewsApiProvider.getNews(earliestPublishDate, offset);
        List<News> news = NewsMapper.mapToNews(newsFromApi);
        news = newsLocationAssignerService.assignLocationsToNews(news);
        saveNews(news);
        return news;
    }

    @Transactional
    public void saveNews(List<News> newsList) {
        for (News news : newsList) {
            if (news.getCities() != null) {
                Set<City> cities = news.getCities();
                cities.forEach(city -> System.out.println("CITY: " + city.getCityName()));
            }
            if (news.getStates() != null) {
                Set<State> states = news.getStates();
                states.forEach(state -> System.out.println("STATE: " + state.getStateName()));
            }
        }
        newsRepository.saveAll(newsList);
    }
}
