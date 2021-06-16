package com.finnal.blogit.entity;

import com.finnal.blogit.entity.enumtype.AccountStatus;
import com.finnal.blogit.entity.enumtype.converter.AccountStatusConverter;
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

    @Column
    @Convert(converter = AccountStatusConverter.class)
    private AccountStatus status;

    @Column(name = "reset_pass_token",length = 45)
    private String token;

    @OneToOne(mappedBy = "userAccount", cascade = CascadeType.ALL)
    private UserDetailEntity userDetailEntity;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<RoleEntity> roles;

    @OneToMany(mappedBy = "userAccount",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<ArticleEntity> articles;

    @OneToOne(mappedBy = "userAccount",cascade = CascadeType.ALL)
    private HistoryEntity history;

    @OneToOne(mappedBy = "userAccount",cascade = CascadeType.ALL)
    private FavoriteEntity favoriteArticle;

    @OneToOne(mappedBy = "userAccount",cascade = CascadeType.ALL)
    private ReadLaterEntity readLater;


}