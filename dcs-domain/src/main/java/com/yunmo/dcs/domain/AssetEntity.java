package com.yunmo.dcs.domain;

import com.yunmo.domain.common.Audited;
import com.yunmo.domain.common.Problems;
import com.yunmo.generator.annotation.AutoValueDTO;
import com.yunmo.generator.annotation.ValueField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Optional;

/** @author lh createTime: 2022-06-27 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AutoValueDTO
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AssetEntity extends Audited {
  private static final String SEARCH = "Search";
  private static final String DOCUMENTS_CREATE = "DocumentsCreate";

  private static final String NAME_CREATE = "NameCreate";
  @Id
  @GenericGenerator(name = "sequence_id", strategy = "com.yunmo.id.HibernateIdentifierGenerator")
  @GeneratedValue(generator = "sequence_id")
  @Column(columnDefinition = "bigint")
  private Long id;

  /** 工单正在进行中的实体，project_id */
  @Schema(description = "工单正在进行中的实体，project_id")
  @Column(columnDefinition = "bigint comment '工单正在进行中的实体，project_id'")
  private Long projectId;

  /** REGION 区域 BAY 间隔 EQUIPMENT 设施 */
  @Schema(description = "REGION  区域 BAY  间隔 EQUIPMENT  设施")
  @NotNull
  @ValueField(value = {SEARCH})
  @Column(columnDefinition = "varchar(200) comment 'REGION  区域 BAY  间隔 EQUIPMENT  设施'")
  @Enumerated(value = EnumType.STRING)
  private EntityType entityType;

  @Column(columnDefinition = "varchar(45)")
  @ValueField
  @Schema(description = "设备类型,格式:一次设备_主变")
  private String category;

  @NotNull
  @Column(columnDefinition = "varchar(45)")
  @ValueField(value = {NAME_CREATE})
  private String name;

  @Column(columnDefinition = "bigint")
  @ValueField(value = {SEARCH})
  private Long parentId;

  @Column(columnDefinition = "bigint")
  private Long workOrderId;

  @Column(columnDefinition = "bigint")
  private Long originalEntityId;

  @Type(type = "json")
  @Column(columnDefinition = "json")
  private Map<String, Object> properties;

  @Column(columnDefinition = "varchar(1000)")
  @ValueField
  private String remark;

  @Column(columnDefinition = "bigint")
  private Long nodeId;

  @Column(columnDefinition = "json")
  @ValueField(value = {DOCUMENTS_CREATE})
  @Schema(description = "设备相册")
  private String documents;

  public long createNodeId(Optional<AssetEntity> parent, Optional<AssetEntity> maxChildNode) {
    long parentNodeId = parent.map(AssetEntity::getNodeId).orElse(0L);
    return maxChildNode
        .map(
            node -> {
              Problems.ensure(node.nodeId % 1000 < 999, "子节点数量超限");
              return node.nodeId + 1;
            })
        .orElse(parentNodeId * 1000 + 1);
  }

  @Schema(description = "REGION  区域 BAY  间隔 EQUIPMENT  设施")
  public enum EntityType {
    REGION,
    BAY,
    EQUIPMENT,
  }
}
