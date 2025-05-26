package com.maxdlr.p13.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maxdlr.p13.entity.RoleEntity;

@org.springframework.stereotype.Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

  Optional<RoleEntity> findOneById(Integer id);
}
