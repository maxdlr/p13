package com.maxdlr.p13.repository;

import org.springframework.data.repository.Repository;

import com.maxdlr.p13.entity.WsConversationEntity;

public interface WsConversationRepository
    extends Repository<WsConversationEntity, Long>, CrudRepository<WsConversationEntity> {
}
