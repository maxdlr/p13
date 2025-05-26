package com.maxdlr.p13.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.maxdlr.p13.entity.MessageEntity;

public interface MessageRepository extends Repository<MessageEntity, Long>, CrudRepository<MessageEntity> {
  List<MessageEntity> findByUserId(Integer userId);
}
