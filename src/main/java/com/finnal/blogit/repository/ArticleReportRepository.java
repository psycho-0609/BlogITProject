package com.finnal.blogit.repository;

import com.finnal.blogit.dto.response.GetListReport;
import com.finnal.blogit.dto.response.ListReportArticleDTO;
import com.finnal.blogit.entity.ArticleReportEntity;
import com.finnal.blogit.entity.enumtype.ArticleReportNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleReportRepository extends JpaRepository<ArticleReportEntity,Long> {

    @Query("select new com.finnal.blogit.dto.response.GetListReport(count (ar), ar.articleEntity.id) from ArticleReportEntity as ar where ar.articleEntity.id in (:ids) group by ar.articleEntity.id order by ar.createdDate desc")
    List<GetListReport> getListReport(@Param("ids") List<Long> ids);

    @Query("select ar.articleEntity.id from ArticleReportEntity as ar where ar.news =:news and ar.id in (:ids)")
    List<Long> getListArticleIdNews(@Param("news") ArticleReportNews news, @Param("ids") List<Long> ids);

    @Query("select new com.finnal.blogit.dto.response.ListReportArticleDTO(ar.id, ar.content, ar.news, ar.createdDate, ar.reportEntity.id, ar.reportEntity.name) from ArticleReportEntity as ar where ar.id in (:ids) order by ar.createdDate desc")
    List<ListReportArticleDTO> getListReportListId(@Param("ids") List<Long> ids);

    void deleteAllByArticleEntityId(Long id);

    @Query("select ar from ArticleReportEntity ar where ar.articleEntity.id =:id and ar.news = 1")
    List<ArticleReportEntity> findAllByArticleEntityIdAndNews(@Param("id") Long id);

    @Query("select ar.id from ArticleReportEntity ar where ar.articleEntity.id = :articleId")
    Page<Long> getListIdForPagi(Pageable pageable, @Param("articleId") Long articleId);

    Long countAllByArticleEntityId(Long id);



}
