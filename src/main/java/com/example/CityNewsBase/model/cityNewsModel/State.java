package com.example.CityNewsBase.model.cityNewsModel;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "state")
public class State {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "state_name", nullable = false)
    @NotNull(message = "Author cannot be null")
    private String stateName;

    @ManyToMany(mappedBy = "states")
    private Set<News> news = new HashSet<>();
}
