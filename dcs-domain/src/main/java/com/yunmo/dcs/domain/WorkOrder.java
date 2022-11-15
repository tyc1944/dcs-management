package com.yunmo.dcs.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yunmo.domain.common.Audited;
import com.yunmo.generator.annotation.AutoValueDTO;
import com.yunmo.generator.annotation.ValueField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;

/** @author lh createTime: 2022-06-27 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AutoValueDTO
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class WorkOrder extends Audited {
  private static final String DOCUMENTS_CREATE = "DocumentsCreate";
  
  @Id
  @GenericGenerator(name = "sequence_id", strategy = "com.yunmo.id.HibernateIdentifierGenerator")
  @GeneratedValue(generator = "sequence_id")
  @Column(columnDefinition = "bigint")
  private Long id;

  @ValueField
  private String name;

  @Column(columnDefinition = "varchar(45)")
  @Enumerated(value = EnumType.STRING)
  @ValueField
  private WorkStatus workStatus;

  @Column(columnDefinition = "bigint")
  @ValueField
  private Long workerId;

  @Column(columnDefinition = "varchar(1000)")
  @ValueField
  private String remark;

  @Column(columnDefinition = "json")
  @ValueField(value = {DOCUMENTS_CREATE})
  @Schema(description = "工单图纸相册")
  private String documents;

  @ValueField
  private Instant planStartTime;

  @ValueField
  private Instant planEndTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Project project;

  @ValueField
  @Embedded private Location location;

  private Instant closeTime;

  private Instant finishTime;

  private String closeReason;

  @Embedded private Contact contact;

  @ValueField
  private WorkType workType;

  @ValueField
  @Column(columnDefinition = "varchar(1000)")
  private String content;

  @Schema(description = "PROCESSING 进行中, FINISHED 已完成,已关闭 CLOSED")
  public enum WorkStatus {
    PROCESSING,
    FINISHED,
    CLOSED,
  }

  public enum WorkType{
    COLLECTION,
    MODEL,
  }
}
