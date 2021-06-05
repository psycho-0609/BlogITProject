package com.ckfinder.demo.repository;

import com.ckfinder.demo.entity.ArticleReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleReportRepository extends JpaRepository<ArticleReportEntity,Long> {
}
