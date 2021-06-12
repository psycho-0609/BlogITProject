package com.finnal.blogit.entity;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private UserAccountEntity userAccount;

    @OneToMany(mappedBy = "historyEntity",fetch = FetchType.LAZY)
    private List<HistoryArticleEntity> historyArticleEntities;


}
