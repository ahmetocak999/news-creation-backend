package com.example.News.Controller;

import com.example.News.DTO.NewsDTO;
import com.example.News.Repo.NewsRepo;
import com.example.News.Service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    NewsService newsService;

    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO>getNewsById(@PathVariable int id){
        NewsDTO newsDTO = newsService.getNewsById(id);
        return ResponseEntity.ok(newsDTO);

    }
    @PostMapping("/add")
    public ResponseEntity<String> createNews(@RequestBody NewsDTO newsDTO) {
        newsService.createNews(newsDTO);
        return ResponseEntity.ok("News is created");
    }

}
