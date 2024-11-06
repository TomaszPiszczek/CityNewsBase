package com.example.CityNewsBase.service;

import com.example.CityNewsBase.model.cityNewsModel.City;
import com.example.CityNewsBase.model.cityNewsModel.News;
import com.example.CityNewsBase.model.cityNewsModel.State;
import com.example.CityNewsBase.model.newsClassification.NewsLocationClassification;
import com.example.CityNewsBase.model.newsClassification.NewsType;
import com.example.CityNewsBase.repository.CityRepository;
import com.example.CityNewsBase.repository.NewsRepository;
import com.example.CityNewsBase.repository.StateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewsLocationAssignerServiceTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private StateRepository stateRepository;

    @Mock
    private OpenAiAnalyzerService openAiAnalyzerService;

    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    private NewsLocationAssignerService newsLocationAssignerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAssignLocationsToNews_LocalNews() {
        // Arrange
        News news1 = new News();
        news1.setTitle("Local News Example");

        List<News> newsList = List.of(news1);

        NewsLocationClassification classification1 = new NewsLocationClassification();
        classification1.setNewsType(NewsType.LOCAL);
        classification1.setCityNames(Set.of("Sample City"));
        classification1.setStateNames(Set.of("Sample State"));

        when(openAiAnalyzerService.getClassificationResultsFromChat(newsList)).thenReturn(List.of(classification1));

        City city = new City();
        city.setCityName("Sample City");

        State state = new State();
        state.setStateName("Sample State");

        when(cityRepository.findByNameAndStateIgnoreCase("Sample City", "Sample State")).thenReturn(Optional.of(city));
        when(stateRepository.findByNameIgnoreCase("Sample State")).thenReturn(Optional.of(state));

        // Act
        List<News> result = newsLocationAssignerService.assignLocationsToNews(newsList);

        // Assert
        assertEquals(1, result.size());
        assertFalse(result.get(0).isGlobalNews());
        assertEquals(1, result.get(0).getCities().size());
        assertEquals("Sample City", result.get(0).getCities().iterator().next().getCityName());
        assertEquals(1, result.get(0).getStates().size());
        assertEquals("Sample State", result.get(0).getStates().iterator().next().getStateName());

    }

    @Test
    void testAssignLocationsToNews_GlobalNews() {
        // Arrange
        News news1 = new News();
        news1.setTitle("Global News Example");

        List<News> newsList = List.of(news1);

        NewsLocationClassification classification1 = new NewsLocationClassification();
        classification1.setNewsType(NewsType.GLOBAL);

        when(openAiAnalyzerService.getClassificationResultsFromChat(newsList)).thenReturn(List.of(classification1));

        // Act
        List<News> result = newsLocationAssignerService.assignLocationsToNews(newsList);

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.get(0).isGlobalNews());
        assertEquals(0, result.get(0).getCities().size());
        assertEquals(0, result.get(0).getStates().size());

        verify(newsRepository, never()).save(news1);
    }

    @Test
    void testAssignLocationsToNews_MultipleNewsItems() {
        // Arrange
        News news1 = new News();
        news1.setTitle("Local News 1");
        News news2 = new News();
        news2.setTitle("Global News 2");

        List<News> newsList = List.of(news1, news2);

        NewsLocationClassification classification1 = new NewsLocationClassification();
        classification1.setNewsType(NewsType.LOCAL);
        classification1.setCityNames(Set.of("City A"));
        classification1.setStateNames(Set.of("State A"));

        NewsLocationClassification classification2 = new NewsLocationClassification();
        classification2.setNewsType(NewsType.GLOBAL);

        when(openAiAnalyzerService.getClassificationResultsFromChat(newsList)).thenReturn(List.of(classification1, classification2));

        City cityA = new City();
        cityA.setCityName("City A");

        State stateA = new State();
        stateA.setStateName("State A");

        when(cityRepository.findByNameAndStateIgnoreCase("City A", "State A")).thenReturn(Optional.of(cityA));
        when(stateRepository.findByNameIgnoreCase("State A")).thenReturn(Optional.of(stateA));

        // Act
        List<News> result = newsLocationAssignerService.assignLocationsToNews(newsList);

        // Assert
        assertEquals(2, result.size());

        News resultNews1 = result.get(0);
        assertFalse(resultNews1.isGlobalNews());
        assertEquals(1, resultNews1.getCities().size());
        assertEquals("City A", resultNews1.getCities().iterator().next().getCityName());
        assertEquals(1, resultNews1.getStates().size());
        assertEquals("State A", resultNews1.getStates().iterator().next().getStateName());

        // Check second news item (Global)
        News resultNews2 = result.get(1);
        assertTrue(resultNews2.isGlobalNews());
        assertEquals(0, resultNews2.getCities().size());
        assertEquals(0, resultNews2.getStates().size());

    }
}
