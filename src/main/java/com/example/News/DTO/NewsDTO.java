package com.example.News.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {

    private int newsId;
    private String title;
    private String body;
    private String summary;
}
