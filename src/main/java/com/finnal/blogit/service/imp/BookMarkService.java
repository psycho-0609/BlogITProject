package com.finnal.blogit.service.imp;

import com.finnal.blogit.dto.response.GetListBookMark;
import com.finnal.blogit.entity.BookMarkEntity;
import com.finnal.blogit.entity.UserAccountEntity;
import com.finnal.blogit.repository.ArticleRepository;
import com.finnal.blogit.repository.BookMarkRepository;
import com.finnal.blogit.service.inter.IBookMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookMarkService implements IBookMarkService {

    @Autowired
    private BookMarkRepository bookMarkRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public BookMarkEntity create(Long accountId, Long articleId) {
        BookMarkEntity entity;
        Optional<BookMarkEntity> readLaterArticleOp = bookMarkRepository.findByAccount_IdAndArticleEntity_Id(accountId, articleId);
        if (readLaterArticleOp.isPresent()) {
            entity = readLaterArticleOp.get();
        } else {
            entity = new BookMarkEntity();
            entity.setAccount(new UserAccountEntity(accountId));
            entity.setArticleEntity(articleRepository.findById(articleId).get());
        }
        entity.setCreatedDate(LocalDateTime.now());
        return bookMarkRepository.save(entity);
    }

    @Override
    public Optional<BookMarkEntity> findByAccountIdAndArticleId(Long accountId, Long articleId) {
        return bookMarkRepository.findByAccount_IdAndArticleEntity_Id(accountId, articleId);
    }

    @Override
    public Optional<BookMarkEntity> findById(Long id) {
        return bookMarkRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        bookMarkRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        bookMarkRepository.deleteAll();
    }

    @Override
    public Long countItemByReadLaterId(Long id) {
        return null;
    }

    @Override
    public List<GetListBookMark> findAllByAccountId(List<Long> ids) {
        return bookMarkRepository.getListReadLaterByAccountId(ids);
    }

    @Override
    public Long countAllByArticleId(Long id) {
        return bookMarkRepository.countByArticleEntityId(id);
    }

    @Override
    public Page<Long> getListIdByUserId(Pageable pageable, Long userId, String title) {
        return bookMarkRepository.getByUsaerId(pageable, userId, title);
    }

    @Override
    public Long countByUserId(Long userId) {
        return bookMarkRepository.countAllByAccountId(userId);
    }

    @Override
    public Long countTotalOfUser(Long userId) {
        return bookMarkRepository.countTotalOfUser(userId);
    }
}
