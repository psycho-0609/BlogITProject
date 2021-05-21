package com.ckfinder.demo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "article")
@Data
@Getter
@Setter
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
    private String image;

    @Column(name = "status")
    private Boolean isPublic;

    @Column
    private Boolean isNew;

    @ManyToOne
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
