
package com.maxdlr.p13.entity;

import com.maxdlr.p13.enums.ConversationStatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = false)
@Data
@Table(name = "conversations", uniqueConstraints = { @UniqueConstraint(columnNames = "wsTopic") })
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ConversationEntity extends AbstractEntity {

  @Id
  @GeneratedValue
  private Integer id;

  @Column(nullable = false)
  private String wsTopic;

  @ManyToOne(fetch = FetchType.LAZY)
  private UserEntity user;

  @Column(nullable = false)
  private ConversationStatusEnum status;
}
