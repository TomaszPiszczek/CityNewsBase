package com.example.CityNewsBase;

import com.example.CityNewsBase.model.cityNewsModel.News;
import com.example.CityNewsBase.service.NewsService;
import com.example.CityNewsBase.temp.CityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;
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
    public ResponseEntity<List<News>> newsa(
            @RequestParam("date") String date,
            @RequestParam("offset") int offset) {

        return ResponseEntity.ok(newsAnalyzer.getNewsWithLocation(date, offset));
    }
}