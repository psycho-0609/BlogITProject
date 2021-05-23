package com.ckfinder.demo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "readlater")
@Data
@Getter
@Setter
public class ReadLaterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "readLaterEntity",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ReadLaterArticleEntity> readLaterArticleEntities;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private UserAccountEntity userAccount;

}
