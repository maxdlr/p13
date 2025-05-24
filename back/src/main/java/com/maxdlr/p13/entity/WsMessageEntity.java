package com.maxdlr.p13.entity;

import org.springframework.data.annotation.Id;

import com.maxdlr.p13.enums.WsMessageStatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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

  @ManyToOne
  private UserEntity user;

  @ManyToOne
  private WsConversationEntity wsConversation;

  @Column(nullable = false)
  private WsMessageStatusEnum status;
}
