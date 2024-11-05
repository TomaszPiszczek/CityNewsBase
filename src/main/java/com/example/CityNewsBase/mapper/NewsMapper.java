package com.example.CityNewsBase.mapper;

import com.example.CityNewsBase.model.cityNewsModel.News;
import com.example.CityNewsBase.model.worldNewsModel.WorldNews;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class NewsMapper {
    public static News mapToNews(WorldNews article) {
        News news = new News();
        news.setTitle(article.title());
        news.setContent(article.text());
        news.setImgUrl(article.image());
        news.setAuthor(article.author());


        String publishDateString = article.publishDate();
        if (publishDateString != null) {
            news.setPublishDate(convertToDate(publishDateString));
        } else {
            news.setPublishDate(null);
        }

        news.setCities(null);
        news.setStates(null);
        news.setGlobalNews(true);

        return news;
    }
    public static List<News> mapToNews(List<WorldNews> articles) {
        return articles.stream()
                .map(NewsMapper::mapToNews)
                .collect(Collectors.toList());
    }
    private static Date convertToDate(String publishDateString) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(publishDateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
