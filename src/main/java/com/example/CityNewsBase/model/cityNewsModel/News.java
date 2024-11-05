package com.example.CityNewsBase.model.cityNewsModel;

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
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "publish_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date publishDate;

    @Column(name = "img_url", columnDefinition = "TEXT")
    private String imgUrl;

    @Column(name = "author", nullable = false, columnDefinition = "TEXT")
    private String author;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "news_city",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "city_id")
    )
    private Set<City> cities = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "news_state",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "state_id")
    )
    private Set<State> states = new HashSet<>();

    @Column(name = "global_news", nullable = false)
    private boolean globalNews;


}
