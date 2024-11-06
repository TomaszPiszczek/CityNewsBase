package com.example.CityNewsBase.service;

import com.example.CityNewsBase.api.worldNewsApiProvider.WorldNewsApiProvider;
import com.example.CityNewsBase.mapper.NewsMapper;
import com.example.CityNewsBase.model.cityNewsModel.News;
import com.example.CityNewsBase.model.worldNewsModel.WorldNews;
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


    @Scheduled(cron = "0 0 5 * * *")
    public void fetchNewsAt5AM() {
        fetch20NewsAndSave();
    }

    @Scheduled(cron = "0 0 17 * * *")
    public void fetchNewsAt5PM() {
        fetch20NewsAndSave();
    }

    /**
     * Due to API limitation we can get news only 40times per day
     * also concurrent requests are impossible due to 1request/sec requirement
     * @return
     */
    public  void fetch20NewsAndSave() {
        String earliestPublishDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int offset = 0;
        for (int newsFetched = 0; newsFetched < 20; newsFetched++) {
            offset+=10;
            List<News> news = getNewsWithLocation(earliestPublishDate, offset);
            databaseService.saveNews(news);
        }

    }

    private List<News> getNewsWithLocation(String earliestPublishDate, int offset) {
        List<WorldNews> newsFromApi = worldNewsApiProvider.getNews(earliestPublishDate, offset);
        List<News> news = NewsMapper.mapToNews(newsFromApi);
        news = newsLocationAssignerService.assignLocationsToNews(news);
        return news;
    }


}
