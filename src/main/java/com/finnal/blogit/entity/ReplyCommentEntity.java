package com.finnal.blogit.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reply_comment")
@Data
public class ReplyCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity comment;

    private String content;

    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private UserAccountEntity account;
}
