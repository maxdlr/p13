package com.maxdlr.p13.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maxdlr.p13.entity.ConversationEntity;

@org.springframework.stereotype.Repository
public interface ConversationRepository
    extends JpaRepository<ConversationEntity, Integer> {

  List<ConversationEntity> findByUserId(Integer userId);

  Optional<ConversationEntity> findOneById(Integer id);

  List<ConversationEntity> findAllByUserId(Integer userId);
}
