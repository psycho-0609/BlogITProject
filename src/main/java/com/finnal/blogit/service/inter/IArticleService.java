package com.finnal.blogit.service.inter;

import com.finnal.blogit.dto.request.ArticleRequest;
import com.finnal.blogit.dto.response.*;
import com.finnal.blogit.entity.ArticleEntity;
import com.finnal.blogit.entity.enumtype.ArticlePublished;
import com.finnal.blogit.entity.enumtype.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IArticleService {
    List<ArticleEntity> findAll();
    ArticleEntity insertArticle(ArticleRequest articleEntity, Long userId) throws IOException;
    ArticleEntity findOne(Long id);
    ArticleEntity update(ArticleRequest articleRequest);
    Optional<ArticleEntity> findById(Long id);
    void deleteArticle(Long id) throws IOException;
    void plusCountView(ArticleEntity articleEntity);
    ArticleEntity save(ArticleEntity articleEntity);
    List<CustomArticleDTO> findAllByPublishedStatusAndAccount(ArticlePublished published, Long id, ArticleStatus status);
    Long totalCountView(List<Long> ids);
    List<ArticleEntity> findByPrioritize();
    List<CustomArticleDTO> getForPopular();
    Optional<ArticleCustomDTO> getById(Long id);
    Optional<ArticleEntity> findByPrioritize(Integer number);
    List<CustomArticleDTO> findByPublishedAndStatusForWeb(ArticlePublished published, ArticleStatus status);
    Long countAllByStatusAndPublished();
    List<StatisticCustomDTO> getForStatistic();
    List<StatisticPieChartCustom> getStatisticPercent();
    boolean isExistedById(Long id);
    List<StatisticAuthor> getStatisticAuthor();
    List<ArticleEntity> findAllByTopicIdForRelease(Integer topicId, Long articleId);
    List<ArticleEntity> findAllOrderNewsLimit();
    List<ArticleEntity> findAllOrderPopularLimit();
    List<ArticleEntity> findAllOrderRateLimit();
    Page<Long> getIdForPanination(Pageable pageable, ArticlePublished published, ArticleStatus status);
    List<CustomArticleDTO> getListArticleByListId(List<Long> ids);
    Long countArticleByPublishedAndStatus(ArticlePublished published, ArticleStatus status, String title);
    Page<Long> getIdOrderByTopicForPagi(Pageable pageable, Integer topicId);
    Long countArticleByTopic(Integer id);
    Page<Long> getIdByTitleForPagi(Pageable pageable, String title, ArticlePublished published, ArticleStatus status);
    Long countArticleByTitle(String title, ArticlePublished published, ArticleStatus status);
    Page<Long> getListIdPrivate(Pageable pageable, String title, Long userId);
    Long countByStatusAndUserId(ArticleStatus status, Long userId, String title);
    Long countByStatusAndPublishedAndUserId(ArticleStatus status, ArticlePublished published, Long userId, String title);
    Page<Long> findListIdByPublishedAndStatusOfUser(Pageable pageable, String title, ArticleStatus status, ArticlePublished published, Long userId);
    Page<Long> getListIdByStatusAndTitleForPagi(Pageable pageable, ArticleStatus status, String title);
    Long countAllByStatusAndTAndTitleLike(ArticleStatus status, String title);
    Page<Long> getListIdForReport(Pageable pageable);
    Long countAllForReport();


}
