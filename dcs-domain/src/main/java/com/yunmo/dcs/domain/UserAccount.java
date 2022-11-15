package com.yunmo.dcs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yunmo.domain.common.Audited;
import com.yunmo.generator.annotation.AutoValueDTO;
import com.yunmo.generator.annotation.ValueField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/** @author lh */
@Entity
@Getter
@Setter
@Builder
@AutoValueDTO
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(
    indexes = {
      @Index(name = "UK_ACCOUNT_NAME_INDEX", columnList = "accountName"),
    })
public class UserAccount extends Audited {

  @Id
  @GenericGenerator(name = "sequence_id", strategy = "com.yunmo.id.HibernateIdentifierGenerator")
  @GeneratedValue(generator = "sequence_id")
  private Long id;

  @ValueField
  @Column(nullable = false)
  private String accountName;

  @JsonIgnore
  @Column(nullable = false)
  @ValueField
  private String password;

  /** 账户启用 */
  @Column(nullable = false)
  @Builder.Default
  @ValueField
  private boolean enabled = true;

  @ValueField private String avatar;

  @Schema(description = "修改密码时间")
  private Instant changePasswordTime;

  @ValueField
  private String phone;

  @ValueField
  private String email;

  @Type(type = "json")
  @Column(columnDefinition = "json")
  @ValueField private Area area;

  @ValueField private String name;


  public UserAccount setPassword(String password) {
    this.password = password;
    this.changePasswordTime = Instant.now();
    return this;
  }

  @Setter
  @Getter
  @EqualsAndHashCode
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Area{
    @Schema(description = "省")
    private   String  province;

    @Schema(description = "市")
    private   String  city;
  }

}
