package com.maxdlr.p13.entity;

import com.maxdlr.p13.enums.WsMessageStatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class WsMessageEntity extends AbstractEntity {
  @Id
  @GeneratedValue
  private Integer id;

  @Column(nullable = false)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  private UserEntity user;

  @ManyToOne
  private WsConversationEntity wsConversation;

  @Column(nullable = false)
  private WsMessageStatusEnum status;
}
