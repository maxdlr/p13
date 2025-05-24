package com.maxdlr.p13.entity;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
}
