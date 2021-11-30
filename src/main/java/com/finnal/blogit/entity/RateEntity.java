package com.finnal.blogit.entity;

import com.finnal.blogit.entity.enumtype.ScoreRateType;
import com.finnal.blogit.entity.enumtype.converter.ScoreRateTypeConverter;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "rate")
@Data
public class RateEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private UserAccountEntity account;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity article;

    @Convert(converter = ScoreRateTypeConverter.class)
    private ScoreRateType score;
}
