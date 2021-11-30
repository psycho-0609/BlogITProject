package com.finnal.blogit.service.inter;

import com.finnal.blogit.dto.response.GetListReport;
import com.finnal.blogit.dto.response.ListReportArticleDTO;
import com.finnal.blogit.entity.ArticleReportEntity;
import com.finnal.blogit.entity.enumtype.ArticleReportNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IArticleReportService {

    ArticleReportEntity save(ArticleReportEntity entity);
    void delete(Long id);
    Optional<ArticleReportEntity> findById(Long id);
    List<GetListReport> findAll(List<Long> listId);
    List<ListReportArticleDTO> findAllReportByListId(List<Long> ids);
    void deleteByArticleId(Long id);
    List<ArticleReportEntity> findAllByArticleIdAndNews(Long id);
    void saveAll(List<ArticleReportEntity> list);
    Page<Long> getListIdForPagi(Pageable pageable, Long articleId);
    Long countAllByArticleId(Long id);

}
