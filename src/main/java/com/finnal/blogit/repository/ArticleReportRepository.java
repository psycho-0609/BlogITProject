package com.finnal.blogit.repository;

import com.finnal.blogit.dto.response.GetListReport;
import com.finnal.blogit.dto.response.ListReportArticleDTO;
import com.finnal.blogit.entity.ArticleReportEntity;
import com.finnal.blogit.entity.enumtype.ArticleReportNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleReportRepository extends JpaRepository<ArticleReportEntity,Long> {

    @Query("select new com.finnal.blogit.dto.response.GetListReport(count (ar), ar.articleEntity.id) from ArticleReportEntity as ar group by ar.articleEntity.id order by ar.createdDate desc")
    List<GetListReport> getListReport();
    @Query("select ar.articleEntity.id from ArticleReportEntity as ar where ar.news =:news")
    List<Long> getListArticleIdNews(@Param("news") ArticleReportNews news);

    @Query("select new com.finnal.blogit.dto.response.ListReportArticleDTO(ar.id, ar.content, ar.news, ar.createdDate, ar.reportEntity.id, ar.reportEntity.name) from ArticleReportEntity as ar where ar.articleEntity.id = :id order by ar.createdDate desc")
    List<ListReportArticleDTO> getListReportByArticleId(@Param("id") Long id);

    void deleteAllByArticleEntityId(Long id);

    @Query("select ar from ArticleReportEntity ar where ar.articleEntity.id =:id and ar.news = 1")
    List<ArticleReportEntity> findAllByArticleEntityIdAndNews(@Param("id") Long id);



}
