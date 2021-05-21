package com.ckfinder.demo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_account")
@Data
@Getter
@Setter
public class UserAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @OneToOne(mappedBy = "userAccount", cascade = CascadeType.ALL)
    private UserDetailEntity userDetailEntity;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<RoleEntity> roles;

    @OneToMany(mappedBy = "userAccount")
    private List<ArticleEntity> articles;

    @OneToOne(mappedBy = "userAccount")
    private HistoryEntity history;

    @OneToOne(mappedBy = "userAccount")
    private FavoriteArticleEntity favoriteArticle;

    @OneToOne(mappedBy = "userAccount")
    private ReadLaterEntity readLater;
}
