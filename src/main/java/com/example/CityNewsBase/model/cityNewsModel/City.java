package com.example.CityNewsBase.model.cityNewsModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "city_name", nullable = false)
    @NotNull(message = "Author cannot be null")
    private String cityName;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @ManyToMany(mappedBy = "cities",cascade = CascadeType.MERGE)
    private Set<News> news = new HashSet<>();
}
