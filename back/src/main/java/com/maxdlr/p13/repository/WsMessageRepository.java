package com.maxdlr.p13.repository;

import org.springframework.data.repository.Repository;

import com.maxdlr.p13.entity.WsMessageEntity;

public interface WsMessageRepository extends Repository<WsMessageEntity, Long>, CrudRepository<WsMessageEntity> {
}
