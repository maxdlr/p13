package com.maxdlr.p13.mapper;

import org.mapstruct.Mapper;

import com.maxdlr.p13.dto.UserRecordInfo;
import com.maxdlr.p13.dto.UserRecordInput;
import com.maxdlr.p13.entity.UserEntity;

@Mapper(componentModel = "spring")
public abstract class UserMapper implements EntityMapper<UserRecordInfo, UserRecordInput, UserEntity> {
}
