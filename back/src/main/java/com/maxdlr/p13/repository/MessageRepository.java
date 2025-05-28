package com.maxdlr.p13.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maxdlr.p13.entity.MessageEntity;

@org.springframework.stereotype.Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {
  List<MessageEntity> findByUserId(Integer userId);

  Optional<MessageEntity> findOneById(Integer id);

  List<MessageEntity> findByConversationId(Integer conversationId);
}
