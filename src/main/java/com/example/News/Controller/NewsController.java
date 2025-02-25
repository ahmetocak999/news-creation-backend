package com.example.News.Controller;

import com.example.News.DTO.NewsDTO;
import com.example.News.Service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    NewsService newsService;

    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO>getAllNews(@PathVariable int id){
        NewsDTO newsDTO = newsService.getNewsById(id);
        return ResponseEntity.ok(newsDTO);

    }

}
