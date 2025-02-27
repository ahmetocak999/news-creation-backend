package com.example.News.Service;

import com.example.News.DTO.NewsDTO;
import com.example.News.Entity.NewsEntity;
import com.example.News.Mapper.NewsMapper;
import com.example.News.Repo.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {
    @Autowired
    private NewsRepo repo;

    private final NewsMapper newsMapper;

    public NewsService(NewsMapper newsMapper) {
        this.newsMapper = newsMapper;
    }

    public void createNews(NewsDTO newsDTO) {
        NewsEntity news = newsMapper.toEntity(newsDTO);
        repo.save(news);

    }

    public NewsDTO getNewsById(int id) {
        NewsEntity news =  repo.findById(id).orElseThrow(() -> new RuntimeException("News not found"));
        return newsMapper.toDTO(news);

    }
}
