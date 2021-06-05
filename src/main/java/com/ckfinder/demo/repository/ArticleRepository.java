package com.ckfinder.demo.repository;

import com.ckfinder.demo.dto.response.CustomArticleDTO;
import com.ckfinder.demo.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<ArticleEntity,Long> {

//    @Query("select new com.ckfinder.demo.dto.response.CustomArticleDTO() from")
//    List<CustomArticleDTO> find();
}
