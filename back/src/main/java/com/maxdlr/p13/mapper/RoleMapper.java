package com.maxdlr.p13.mapper;

import org.mapstruct.Mapper;

import com.maxdlr.p13.dto.RoleRecordInfo;
import com.maxdlr.p13.dto.RoleRecordInput;
import com.maxdlr.p13.entity.RoleEntity;

@Mapper(componentModel = "spring")
public abstract class RoleMapper implements EntityMapper<RoleRecordInfo, RoleRecordInput, RoleEntity> {
}
