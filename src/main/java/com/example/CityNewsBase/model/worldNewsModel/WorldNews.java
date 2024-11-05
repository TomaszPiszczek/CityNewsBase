package com.example.CityNewsBase.model.worldNewsModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WorldNews(
        String title,
        String text,
        String image,
        String author,
        @JsonProperty("publish_date")  String publishDate
) {}
