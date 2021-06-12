package com.finnal.blogit.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "favorite")
@Data
@Getter
@Setter
public class FavoriteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "favoriteEntity",fetch = FetchType.LAZY)
    private List<FavoriteArticleEntity> favouriteArticleEntities;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private UserAccountEntity userAccount;
}
