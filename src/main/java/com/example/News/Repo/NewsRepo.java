package com.example.News.Repo;

import com.example.News.DTO.NewsDTO;
import com.example.News.Entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepo extends JpaRepository<NewsEntity,Integer> {

    @Query("SELECT n FROM NewsEntity n WHERE n.newsId IN :ids")
    List<NewsEntity> findAllByIds(@Param("ids") List<Integer> ids);
}
