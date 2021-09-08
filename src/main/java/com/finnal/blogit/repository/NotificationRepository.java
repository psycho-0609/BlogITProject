package com.finnal.blogit.repository;

import com.finnal.blogit.dto.response.NotificationResponse;
import com.finnal.blogit.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    @Query("select new com.finnal.blogit.dto.response.NotificationResponse(n.id, n.content, n.url, n.createdDate, n.status, n.type) from NotificationEntity n where n.account.id = :id order by n.createdDate desc")
    List<NotificationResponse> findAllByUserId(@Param("id") Long id);

    void deleteAllByAccountId(Long id);

    @Query("select count(n) from NotificationEntity n where n.account.id =:id and n.status = 1")
    Long countAllNotifyNewByUserId(@Param("id") Long id);

    @Modifying
    @Query("update NotificationEntity n set n.status = 2 where n.account.id =:id")
    void readAll(@Param("id") Long id);

    Long countByAccountId(Long id);
}
