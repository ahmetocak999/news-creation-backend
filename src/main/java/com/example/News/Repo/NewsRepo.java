package com.example.News.Repo;

import com.example.News.Entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepo extends JpaRepository<NewsEntity,Integer> {

}
