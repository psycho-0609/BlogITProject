package com.finnal.blogit.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private UserAccountEntity account;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity article;
    private String content;
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "comment")
    private List<ReplyCommentEntity> replyComments;

    public CommentEntity(Long id) {
        this.id = id;
    }
}
