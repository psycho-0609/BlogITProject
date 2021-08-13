package com.finnal.blogit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class StatisticAuthor {
    private Long userAccountId;
    private Long total;
}
