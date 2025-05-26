package com.maxdlr.p13.mapper;

import org.mapstruct.Mapper;

import com.maxdlr.p13.dto.MessageRecordInfo;
import com.maxdlr.p13.dto.MessageRecordInput;
import com.maxdlr.p13.entity.MessageEntity;

@Mapper(componentModel = "spring")
public abstract class MessageMapper
    implements EntityMapper<MessageRecordInfo, MessageRecordInput, MessageEntity> {
}
