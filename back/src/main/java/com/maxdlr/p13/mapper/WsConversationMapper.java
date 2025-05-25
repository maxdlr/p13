package com.maxdlr.p13.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import com.maxdlr.p13.dto.WsConversationRecordInfo;
import com.maxdlr.p13.dto.WsConversationRecordInput;
import com.maxdlr.p13.entity.WsConversationEntity;
import com.maxdlr.p13.repository.UserRepository;

@Mapper(componentModel = "spring")
public abstract class WsConversationMapper
    implements EntityMapper<WsConversationRecordInfo, WsConversationRecordInput, WsConversationEntity> {

  @Autowired
  public UserRepository userRepository;

  @Mappings({
      @Mapping(target = "user", expression = "java(this.userRepository.findOneById(input.getUserId()).get())")
  })
  public abstract WsConversationEntity toEntityFromInput(WsConversationRecordInput input);
}
