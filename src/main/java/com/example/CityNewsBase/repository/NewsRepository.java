package com.example.CityNewsBase.repository;


import com.example.CityNewsBase.model.cityNewsModel.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<News,UUID > {
}
