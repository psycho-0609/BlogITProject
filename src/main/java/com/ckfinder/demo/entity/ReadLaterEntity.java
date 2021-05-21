package com.ckfinder.demo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "read_later")
@Data
@Getter
@Setter
public class ReadLaterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "article_later",
                joinColumns = @JoinColumn(name = "later_id"),
                inverseJoinColumns = @JoinColumn(name = "article_id"))
    private List<ArticleEntity> articles;

    @OneToOne
    @JoinColumn(name = "account_id")
    private UserAccountEntity userAccount;

}
