package com.example.CityNewsBase.service;

import com.example.CityNewsBase.api.worldNewsApiProvider.WorldNewsApiProvider;
import com.example.CityNewsBase.mapper.NewsMapper;
import com.example.CityNewsBase.model.cityNewsModel.News;
import com.example.CityNewsBase.model.worldNewsModel.WorldNews;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class NewsService {

    private final WorldNewsApiProvider worldNewsApiProvider;
    private final NewsLocationAssignerService newsLocationAssignerService;
    private final DatabaseService databaseService;

    public NewsService(WorldNewsApiProvider worldNewsApiProvider, NewsLocationAssignerService newsLocationAssignerService, DatabaseService databaseService) {
        this.worldNewsApiProvider = worldNewsApiProvider;
        this.newsLocationAssignerService = newsLocationAssignerService;
        this.databaseService = databaseService;
    }


    @Scheduled(cron = "0 0 1 * * *")
    public void fetchNewsAt5AM() {
        get20NewsWithLocationWithCurrentDay();
    }
    @Scheduled(cron = "0 0 2 * * *")
    public void fetchNewsAt1AM() {
        get20NewsWithLocationWithCurrentDay();
    }
    @Scheduled(cron = "0 0 3 * * *")
    public void fetchNewsAt3AM() {
        get20NewsWithLocationWithCurrentDay();
    }
    @Scheduled(cron = "0 0 4 * * *")
    public void fetchNewsAt4AM() {
        get20NewsWithLocationWithCurrentDay();
    }





    @Scheduled(cron = "0 59 23 * * *")
    public void fetchNewsAt5PM() {
        get20NewsWithLocationWithCurrentDay();

    }

    /**
     * Due to API limitation we can send only 40 requests per day
     * also concurrent requests are impossible due to 1request/sec requirement
     */
    @Transactional
    public  void get20NewsWithLocationWithCurrentDay() {
        String earliestPublishDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int offset = 0;
        for (int newsFetched = 0; newsFetched < 20; newsFetched++) {
            offset+=10;
            List<News> news = getNewsWithLocation(earliestPublishDate, offset);
            databaseService.saveNews(news);
        }

    }
    @Transactional
    public List<News> getNewsWithLocation(String earliestPublishDate, int offset) {
        List<WorldNews> newsFromApi = worldNewsApiProvider.getNews(earliestPublishDate, offset);
        List<News> news = NewsMapper.mapToNews(newsFromApi);
        news = newsLocationAssignerService.assignLocationsToNews(news);
        return news;
    }


}
