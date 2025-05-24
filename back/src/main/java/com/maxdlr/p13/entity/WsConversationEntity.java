
package com.maxdlr.p13.entity;

import org.springframework.data.annotation.Id;

import com.maxdlr.p13.enums.WsConversationStatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class WsConversationEntity {
  @Id
  @GeneratedValue
  private Integer id;

  @Column(nullable = false)
  private String wsTopicId;

  @Column(nullable = false)
  private UserEntity user;

  @Column(nullable = false)
  private WsConversationStatusEnum status;
}
