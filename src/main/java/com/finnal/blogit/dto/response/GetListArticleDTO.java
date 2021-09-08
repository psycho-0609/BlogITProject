package com.finnal.blogit.dto.response;

import lombok.Data;
import org.springframework.data.domain.Page;


@Data
public class GetListArticleDTO {
    private Page<CustomArticleDTO> listArticle;
    private Long totalPage;
}
