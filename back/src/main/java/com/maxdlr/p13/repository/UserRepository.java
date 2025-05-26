package com.maxdlr.p13.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maxdlr.p13.entity.UserEntity;

@org.springframework.stereotype.Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
  Boolean existsByEmail(String email);

  Optional<UserEntity> findOneById(Integer id);
}
