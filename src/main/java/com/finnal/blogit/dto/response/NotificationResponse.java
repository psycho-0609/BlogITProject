package com.finnal.blogit.dto.response;

import com.finnal.blogit.entity.enumtype.NotificationStatusType;
import com.finnal.blogit.entity.enumtype.NotificationTypeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private Long id;
    private String content;
    private String url;
    private LocalDateTime createdDate;
    private NotificationStatusType status;
    private NotificationTypeType type;
    public Integer getStatus() {
        return status == null ? null : status.getValue();
    }

    public Integer getType() {
        return type == null ? null : type.getValue();
    }

    public NotificationResponse(Long id) {
        this.id = id;
    }


}
