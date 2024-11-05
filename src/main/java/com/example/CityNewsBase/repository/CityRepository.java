package com.example.CityNewsBase.repository;

import com.example.CityNewsBase.model.cityNewsModel.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<City, UUID> {

    @Query("SELECT c FROM City c WHERE LOWER(c.cityName) = LOWER(:cityName)")
    Optional<City> findByNameIgnoreCase(@Param("cityName") String cityName);

    @Query("SELECT c FROM City c WHERE LOWER(c.cityName) = LOWER(:cityName) AND LOWER(c.state.stateName) = LOWER(:stateName)")
    Optional<City> findByNameAndStateIgnoreCase(@Param("cityName") String cityName, @Param("stateName") String stateName);

}
