package com.example.CityNewsBase.repository;


import com.example.CityNewsBase.model.cityNewsModel.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StateRepository extends JpaRepository<State, UUID> {
    @Query("SELECT s FROM State s WHERE LOWER(s.stateName) = LOWER(:stateName)")
    Optional<State> findByNameIgnoreCase(@Param("stateName") String stateName);

    State findByStateName(String stateName);
}
