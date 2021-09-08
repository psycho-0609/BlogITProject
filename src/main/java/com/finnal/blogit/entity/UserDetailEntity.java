package com.finnal.blogit.entity;

import com.finnal.blogit.constant.Constant;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_detail")
@Data
@Getter
@Setter
public class UserDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String phone;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private Date dob;

    @Column
    private String thumbnail;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private UserAccountEntity userAccount;

    @Transient
    public String getImagePath(){
        return this.thumbnail == null || thumbnail.equals("") ? "/img/defaultUserImg.jpg": Constant.FIREBASE_URL + Constant.BUCKET_NAME + "/avatarUser/" + id +"/" + thumbnail;
    }
}
