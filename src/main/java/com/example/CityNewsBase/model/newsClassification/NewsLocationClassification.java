package com.example.CityNewsBase.model.newsClassification;


import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class NewsLocationClassification {
    private NewsType newsType;
    private Set<String> stateNames;
    private Set<String> cityNames;
}