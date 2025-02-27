package com.example.News.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int newsId;
    private String title;

    @Column(length = 5000)
    private String body;

    private String summary;
}
