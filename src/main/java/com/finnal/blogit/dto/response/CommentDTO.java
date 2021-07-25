package com.finnal.blogit.dto.response;

import com.finnal.blogit.entity.enumtype.AccountStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class CommentDTO {

    private Long id;
    private String content;
    private LocalDateTime createdDate;
    private ArticleResponse article;
    private CustomUserAccount account;
    private List<ReplyCommentDTO> replyComment;

    public CommentDTO(Long id, String content, LocalDateTime createdDate, Long articleId, Long accountId, String email,
                      AccountStatus status, Long userDetailId, String firstName, String lastName, String thumbnail) {
        this.id = id;
        this.content = content;
        this.createdDate = createdDate;
        this.article = new ArticleResponse(articleId);
        this.account = new CustomUserAccount(accountId, email, status, userDetailId, firstName, lastName, thumbnail);
    }
}
