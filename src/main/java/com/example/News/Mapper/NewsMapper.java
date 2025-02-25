package com.example.News.Mapper;

import com.example.News.DTO.NewsDTO;
import com.example.News.Entity.NewsEntity;
import org.springframework.stereotype.Component;

@Component
public class NewsMapper {

    public NewsDTO toDTO(NewsEntity news) {
        if (news == null) {
            return null;
        }
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setNewsId(news.getNewsId());
        newsDTO.setTitle(news.getTitle());
        newsDTO.setSummary(news.getSummary());
        newsDTO.setBody(news.getBody());

        return newsDTO;
    }

    public NewsEntity toEntity(NewsDTO newsDTO){
        if(newsDTO == null){
            return null;
        }
        NewsEntity news = new NewsEntity();
        news.setNewsId(newsDTO.getNewsId());
        news.setTitle(newsDTO.getTitle());
        news.setSummary(newsDTO.getSummary());
        news.setBody(newsDTO.getBody());
        return news;
    }
}
