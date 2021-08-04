package com.finnal.blogit.service.imp;

import com.finnal.blogit.dto.response.*;
import com.finnal.blogit.entity.CommentEntity;
import com.finnal.blogit.repository.ArticleRepository;
import com.finnal.blogit.repository.CommentRepository;
import com.finnal.blogit.repository.ReplyCommentRepository;
import com.finnal.blogit.service.inter.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private ReplyCommentRepository replyCommentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public CommentEntity save(CommentEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<CommentDTO> getAllByArticleId(Long id) {
        List<ReplyCommentDTO> replyList = replyCommentRepository.findAllByArticleId(id);
        List<CommentDTO> listComment = repository.getAllByArticleId(id);
        Map<Long, List<ReplyCommentDTO>> mapReply = replyList.stream().collect(Collectors.groupingBy(ReplyCommentDTO::getCommentId));
        listComment.stream().forEach(el -> el.setReplyComment(mapReply.get(el.getId())));
        return listComment;
    }

    @Override
    public Optional<CommentEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public List<ListArticleComment> listTotalComment() {
        List<ListArticleComment> list = repository.countComment();
        getListComment(list);
        return list;
    }

    @Override
    public List<ListArticleComment> findByTitle(String title) {
        List<ListArticleComment> list = repository.getAllByLikeTitle(title);
        getListComment(list);
        return list;
    }

    private void getListComment(List<ListArticleComment> list){
        if(list.size() > 0){
            List<TotalReplyComment> replyComments = replyCommentRepository.getTotalComment();
            Map<Long, List<TotalReplyComment>> mapRep = replyComments.stream().collect(Collectors.groupingBy(TotalReplyComment::getArticleId));
            List<Long> articleIds = list.stream().map(ListArticleComment::getArticleId).collect(Collectors.toList());
            List<CustomArticleDTO> listArticles = articleRepository.getListArticle(articleIds);
            Map<Long, List<CustomArticleDTO>> map  = listArticles.stream().collect(Collectors.groupingBy(CustomArticleDTO::getId));
            list.forEach(t -> {
                t.setArticle(map.get(t.getArticleId()).get(0));
                if (mapRep.get(t.getArticleId()) != null) {
                    t.setTotalComment(t.getTotalComment() + mapRep.get(t.getArticleId()).get(0).getTotal());
                }
            });
        }
    }
}
