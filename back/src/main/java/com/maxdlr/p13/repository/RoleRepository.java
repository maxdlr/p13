package com.maxdlr.p13.repository;

import org.springframework.data.repository.Repository;

import com.maxdlr.p13.entity.RoleEntity;

public interface RoleRepository extends Repository<RoleEntity, Long>, CrudRepository<RoleEntity> {
}
