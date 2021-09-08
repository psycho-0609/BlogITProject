package com.finnal.blogit.service.inter;

import com.finnal.blogit.dto.response.GetListNotification;
import com.finnal.blogit.dto.response.NotificationResponse;
import com.finnal.blogit.entity.NotificationEntity;
import java.util.Optional;

public interface INotificationService {

    NotificationResponse save(NotificationEntity entity);
    GetListNotification findAllByUserId(Long id);
    void deleteById(Long id);
    void deleteAllBUserId(Long id);
    NotificationResponse updateStatus(NotificationEntity entity);
    Optional<NotificationEntity> findById(Long id);
    void readedAll(Long id);
    Long countByAccountId(Long id);
    Boolean existedById(Long id);
}
