package com.maxdlr.p13.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import com.maxdlr.p13.dto.ConversationRecordInfo;
import com.maxdlr.p13.dto.ConversationRecordInput;
import com.maxdlr.p13.entity.ConversationEntity;
import com.maxdlr.p13.repository.UserRepository;

@Mapper(componentModel = "spring")
public abstract class ConversationMapper
    implements EntityMapper<ConversationRecordInfo, ConversationRecordInput, ConversationEntity> {

  @Autowired
  public UserRepository userRepository;

  @Mappings({
      @Mapping(target = "user", expression = "java(this.userRepository.findOneById(input.getUserId()).get())")
  })
  public abstract ConversationEntity toEntityFromInput(ConversationRecordInput input);
}
