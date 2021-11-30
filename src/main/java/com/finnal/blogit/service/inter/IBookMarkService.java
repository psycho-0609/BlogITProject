package com.finnal.blogit.service.inter;

import com.finnal.blogit.dto.response.GetListBookMark;
import com.finnal.blogit.entity.BookMarkEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IBookMarkService {
    BookMarkEntity create(Long readLaterId, Long articleId);
    Optional<BookMarkEntity> findByAccountIdAndArticleId(Long accountId, Long articleId);
    Optional<BookMarkEntity> findById(Long id);
    void delete(Long id);
    void deleteAll();
    Long countItemByReadLaterId(Long id);
    List<GetListBookMark> findAllByAccountId(List<Long> id);
    Long countAllByArticleId(Long id);
    Page<Long> getListIdByUserId(Pageable pageable, Long userId, String title);
    Long countByUserId(Long userId);
    Long countTotalOfUser(Long userId);
}
