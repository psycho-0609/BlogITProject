package com.finnal.blogit.dto.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GetHistoryFlowDate {

    private Date date;
    private List<HistoryArticleDTO> historyArticle;
}
