package com.example.CityNewsBase.service;

import com.example.CityNewsBase.model.cityNewsModel.News;
import com.example.CityNewsBase.repository.NewsRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DatabaseService {

    private final NewsRepository newsRepository;
    private final Validator validator;

    public DatabaseService(NewsRepository newsRepository, Validator validator) {
        this.newsRepository = newsRepository;
        this.validator = validator;
    }

    @Transactional
    public void saveNews(List<News> newsList) {
        List<String> existingTitles = fetchExistingTitles();

        List<News> validNewsList = newsList.stream()
                .filter(news -> isTitleUnique(news.getTitle(), existingTitles))
                .filter(this::isValidNews)
                .collect(Collectors.toList());

        if (!validNewsList.isEmpty()) {
            newsRepository.saveAll(validNewsList);
        }
    }

    private List<String> fetchExistingTitles() {
        return newsRepository.findAllTitles();
    }

    private boolean isTitleUnique(String title, List<String> existingTitles) {
        boolean isUnique = !existingTitles.contains(title);
        if (!isUnique) {
            log.info("Duplicate title found, skipping entry: {}", title);
        }
        return isUnique;
    }

    private boolean isValidNews(News news) {
        Set<ConstraintViolation<News>> violations = validator.validate(news);
        if (!violations.isEmpty()) {
            logValidationErrors(news, violations);
            return false;
        }
        return true;
    }

    private void logValidationErrors(News news, Set<ConstraintViolation<News>> violations) {
        for (ConstraintViolation<News> violation : violations) {
            log.error("Validation failed for News: {}. Error: {}", news, violation.getMessage());
        }
    }
}
