package com.finnal.blogit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListArticleComment {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long articleId;
    private CustomArticleDTO article;
    private Long totalComment;

    public ListArticleComment(Long articleId, Long totalComment) {
        this.articleId = articleId;
        this.totalComment = totalComment;
    }
}
