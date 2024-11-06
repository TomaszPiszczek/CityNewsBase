package com.example.CityNewsBase.model.cityNewsModel;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    @NotNull(message = "Title cannot be null")
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    @NotNull(message = "Content cannot be null")
    private String content;

    @Column(name = "publish_date", nullable = false)
    @NotNull(message = "Date cannot be null")
    @Temporal(TemporalType.DATE)
    private Date publishDate;

    @Column(name = "img_url", columnDefinition = "TEXT")
    @NotNull(message = "Image cannot be null")
    private String imgUrl;

    @Column(name = "author", nullable = false, columnDefinition = "TEXT")
    @NotNull(message = "Author cannot be null")
    private String author;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "news_city",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "city_id")
    )
    private Set<City> cities = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "news_state",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "state_id")
    )
    private Set<State> states = new HashSet<>();

    @Column(name = "global_news", nullable = false)
    @NotNull(message = "Author cannot be null")
    private boolean globalNews;


}
