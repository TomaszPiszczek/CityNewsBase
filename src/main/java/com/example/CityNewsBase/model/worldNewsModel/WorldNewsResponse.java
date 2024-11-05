package com.example.CityNewsBase.model.worldNewsModel;

import java.util.List;

public record WorldNewsResponse(
        int offset,
        int number,
        int available,
        List<WorldNews> news
) {}
