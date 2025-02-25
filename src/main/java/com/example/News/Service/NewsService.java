package com.example.News.Service;

import com.example.News.DTO.NewsDTO;
import com.example.News.Entity.NewsEntity;
import com.example.News.Mapper.NewsMapper;
import com.example.News.Repo.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NewsService {

    private final NewsMapper newsMapper;
    private final NewsRepo newsRepo;

    public NewsService(NewsMapper newsMapper, NewsRepo newsRepo) {
        this.newsMapper = newsMapper;
        this.newsRepo = newsRepo;
    }

    public NewsDTO getNewsById(int id) {
        NewsEntity news = newsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("The news is not found"));
        return newsMapper.toDTO(news);
    }
}

