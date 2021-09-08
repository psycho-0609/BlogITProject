package com.finnal.blogit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finnal.blogit.entity.enumtype.AccountStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReplyCommentDTO {
    private Long id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long commentId;
    private Long articleId;
    private String content;
    private LocalDateTime createdDate;
    private CustomUserAccount account;
    private ReplyAccountDTO replyAccount;


    public ReplyCommentDTO(Long id, Long commentId, String content, LocalDateTime createdDate, Long accountId, String email,
                           AccountStatus status, Long userDetailId,  String firstName, String lastName, String thumbnail, Long repAccountId, String repFirstName, String repLastName){
        this.id = id;
        this.commentId = commentId;
        this.content = content;
        this.createdDate = createdDate;
        this.account = new CustomUserAccount(accountId, email, status, userDetailId, firstName, lastName, thumbnail);
        if(repAccountId == null){
            this.replyAccount = null;
        }else{
            this.replyAccount = new ReplyAccountDTO(repAccountId, repFirstName, repLastName);
        }
    }
}
