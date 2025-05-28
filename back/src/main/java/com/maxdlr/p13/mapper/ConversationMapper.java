package com.maxdlr.p13.mapper;

import org.mapstruct.Mapper;

import com.maxdlr.p13.dto.ConversationRecordInfo;
import com.maxdlr.p13.dto.ConversationRecordInput;
import com.maxdlr.p13.entity.ConversationEntity;

@Mapper(componentModel = "spring")
public abstract class ConversationMapper
    implements EntityMapper<ConversationRecordInfo, ConversationRecordInput, ConversationEntity> {
}
