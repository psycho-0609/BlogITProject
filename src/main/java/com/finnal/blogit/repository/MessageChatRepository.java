package com.finnal.blogit.repository;

import com.finnal.blogit.dto.response.ChatResponse;
import com.finnal.blogit.entity.MessageChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageChatRepository extends JpaRepository<MessageChat, Long> {

    @Query("select new com.finnal.blogit.dto.response.ChatResponse(mc.id, mc.fromAccount.id, mc.toAccount.id, mc.content, mc.createdDate) " +
            "from MessageChat mc where (mc.fromAccount.id = :userId and mc.toAccount.id = :otherId) or (mc.fromAccount.id = :otherId and mc.toAccount.id = :userId) order by mc.createdDate asc " )
    List<ChatResponse> findAllByUserIdAndOtherUserId(@Param("userId") Long userId, @Param("otherId") Long otherId);

}
