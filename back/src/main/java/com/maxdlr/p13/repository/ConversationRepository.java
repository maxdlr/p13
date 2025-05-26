package com.maxdlr.p13.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.maxdlr.p13.entity.ConversationEntity;

public interface ConversationRepository
    extends Repository<ConversationEntity, Long>, CrudRepository<ConversationEntity> {

  List<ConversationEntity> findAllByUserId(Integer userId);
}
