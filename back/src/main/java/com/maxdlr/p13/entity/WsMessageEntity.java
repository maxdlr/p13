package com.maxdlr.p13.entity;

import com.maxdlr.p13.enums.WsMessageStatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@EqualsAndHashCode
@Data
@Table(name = "ws_message")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
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
