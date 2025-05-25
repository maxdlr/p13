package com.maxdlr.p13.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;

import com.maxdlr.p13.dto.WsMessageRecordInfo;
import com.maxdlr.p13.dto.WsMessageRecordInput;
import com.maxdlr.p13.entity.WsMessageEntity;

@Mapper(componentModel = "spring")
public abstract class WsMessageMapper
    implements EntityMapper<WsMessageRecordInfo, WsMessageRecordInput, WsMessageEntity> {

  // @Mappings({
  // @Mapping(target = "")
  // })
  // public abstract WsMessageEntity toEntityFromInfo();
}
