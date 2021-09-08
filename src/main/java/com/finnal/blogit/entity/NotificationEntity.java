package com.finnal.blogit.entity;

import com.finnal.blogit.entity.enumtype.NotificationStatusType;
import com.finnal.blogit.entity.enumtype.NotificationTypeType;
import com.finnal.blogit.entity.enumtype.converter.NotificationStatusTypeConverter;
import com.finnal.blogit.entity.enumtype.converter.NotificationTypeTypeConverter;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "notification")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String url;

    @Column
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private UserAccountEntity account;

    @Convert(converter = NotificationStatusTypeConverter.class)
    private NotificationStatusType status;

    @Convert(converter = NotificationTypeTypeConverter.class)
    private NotificationTypeType type;
}
