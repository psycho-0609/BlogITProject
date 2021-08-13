package com.finnal.blogit.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class MessageChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private UserAccountEntity fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private UserAccountEntity toAccount;

    private String content;

    @Column(name = "created_date")
    private Date createdDate;
}
