package com.finnal.blogit.service.imp;

import com.finnal.blogit.dto.response.GetListNotification;
import com.finnal.blogit.dto.response.NotificationResponse;
import com.finnal.blogit.entity.NotificationEntity;
import com.finnal.blogit.entity.enumtype.NotificationStatusType;
import com.finnal.blogit.repository.NotificationRepository;
import com.finnal.blogit.service.inter.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    private NotificationRepository repository;

    @Override
    public NotificationResponse save(NotificationEntity entity) {
        entity.setStatus(NotificationStatusType.NEW);
        entity.setCreatedDate(LocalDateTime.now());
        entity = repository.save(entity);
        return new NotificationResponse(entity.getId(), entity.getContent(), entity.getUrl(), entity.getCreatedDate(), entity.getStatus(), entity.getType());
    }

    @Override
    public GetListNotification findAllByUserId(Long id) {
        return new GetListNotification(repository.countAllNotifyNewByUserId(id), repository.findAllByUserId(id));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllBUserId(Long id) {
        repository.deleteAllByAccountId(id);
    }

    @Override
    public NotificationResponse updateStatus(NotificationEntity entity) {
        entity.setStatus(NotificationStatusType.NOT);
        repository.save(entity);
        return new NotificationResponse(entity.getId());
    }

    @Override
    public Optional<NotificationEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void readedAll(Long id) {
        repository.readAll(id);
    }

    @Override
    public Long countByAccountId(Long id) {
        return repository.countByAccountId(id);
    }

    @Override
    public Boolean existedById(Long id) {
        return repository.existsById(id);
    }
}
