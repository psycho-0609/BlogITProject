package com.ckfinder.demo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "favorite_article")
@Data
@Getter
@Setter
public class FavoriteArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "actice_favorite",
                joinColumns = @JoinColumn(name = "favorite_id"),
                inverseJoinColumns = @JoinColumn(name = "article_id"))
    private List<ArticleEntity> articles;

    @OneToOne
    @JoinColumn(name = "account_id")
    private UserAccountEntity userAccount;
}
