package com.ckfinder.demo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "history")
@Data
@Getter
@Setter
public class HistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id")
    private UserAccountEntity userAccount;

    @ManyToMany
    @JoinTable(name = "article_history",
                joinColumns = @JoinColumn(name = "history_id"),
                inverseJoinColumns = @JoinColumn(name = "article_id"))
    private List<ArticleEntity> articles;


}
