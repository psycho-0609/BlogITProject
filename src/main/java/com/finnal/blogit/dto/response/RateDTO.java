package com.finnal.blogit.dto.response;

import com.finnal.blogit.entity.enumtype.ScoreRateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateDTO {
    private Long id;
    private Long articleId;
    private ScoreRateType score;

    public Integer getScore() {
        return score == null ? null : score.getValue();
    }
}
