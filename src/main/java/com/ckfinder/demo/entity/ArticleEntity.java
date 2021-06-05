package com.ckfinder.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "article")
@Data
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column
    private String content;

    @Column
    private String videoFile;

    @Column
    private String shortDescription;

    @Column
    private String image;

    @Column(name = "status")
    private Boolean isPublic;

    @Column
    private Boolean isNew;

    @OneToMany(mappedBy = "articleEntity")
    private List<ArticleReportEntity> articleReportEntities;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private TopicEntity topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    private UserAccountEntity userAccount;

    @Column
    private Long countView;

    @Transient
    public String getImagePath(){
        if(image == null|| id==null) return null;
        return "/video/" + id + "/" + image;
    }

}
