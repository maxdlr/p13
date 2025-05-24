package com.maxdlr.p13.repository;

import org.springframework.data.repository.Repository;

import com.maxdlr.p13.entity.UserEntity;

public interface UserRepository extends Repository<UserEntity, Long>, CrudRepository<UserEntity> {
  Boolean existsByEmail(String email);
}
