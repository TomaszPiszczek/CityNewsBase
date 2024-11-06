package com.example.CityNewsBase.repository;


import com.example.CityNewsBase.model.cityNewsModel.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<News,UUID > {

        boolean existsByTitle(String title);
        @Query("SELECT n.title FROM News n")
        List<String> findAllTitles();

}
