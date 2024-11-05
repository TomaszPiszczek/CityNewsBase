package com.example.CityNewsBase.model.newsClassification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ClassificationResult {
    @JsonProperty("newsScope")
    private String newsScope;

    @JsonProperty("citynames")
    private Set<String> cityNames;

    @JsonProperty("statenames")
    private Set<String> stateNames;
}
