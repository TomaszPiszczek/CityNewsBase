package com.example.CityNewsBase.controller;

import com.example.CityNewsBase.model.cityNewsModel.News;
import com.example.CityNewsBase.service.NewsService;
import com.example.CityNewsBase.temp.CityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class NewsController {
   private final NewsService newsAnalyzer;
   private  final CityService cityService;

    @PostMapping("/add")
    public ResponseEntity<?> addCities(@RequestBody Map<String, List<String>> states) {
        if (states == null) {
            return ResponseEntity.badRequest().body("The states map cannot be null");
        }
        cityService.addCities(states);
        return ResponseEntity.ok("Cities added successfully");
    }
    @GetMapping("/askChat")
    public ResponseEntity<List<News>> newsa(){
     return ResponseEntity.ok(newsAnalyzer.getNewsWithLocation("2024-11-05",1));
    }
}
