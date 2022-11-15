package com.yunmo.dcs.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yunmo.domain.common.Audited;
import com.yunmo.generator.annotation.AutoValueDTO;
import com.yunmo.generator.annotation.ValueField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

/** @author lh createTime: 2022-06-27 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AutoValueDTO
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Project extends Audited {
  private static final String DOCUMENTS_CREATE = "DocumentsCreate";
  
  @Id
  @GenericGenerator(name = "sequence_id", strategy = "com.yunmo.id.HibernateIdentifierGenerator")
  @GeneratedValue(generator = "sequence_id")
  @Column(columnDefinition = "bigint")
  private Long id;

  @Column(columnDefinition = "varchar(45)")
  @ValueField
  private String name;

  @Enumerated(value = EnumType.STRING)
  @ValueField
  private ProjectType projectType;

  @ValueField
  @Embedded private Location location;

  @Type(type = "json")
  @Column(columnDefinition = "json")
  @ValueField(value = {DOCUMENTS_CREATE})
  private String documents;

  @Schema(description = "项目类型")
  public enum ProjectType {
    POWER_INDUSTRY,
  }

  @Column(columnDefinition = "varchar(1000)")
  @ValueField
  private String content;
}
