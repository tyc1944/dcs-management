package com.yunmo.dcs.infrastruction.view;

import com.yunmo.dcs.domain.AssetEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetEntityView {
    private Long id;

    private Long projectId;

    private AssetEntity.EntityType entityType;

    private String category;

    private String name;

    private Long parentId;

    private Long workOrderId;

    private Long originalEntityId;

    private Map<String, Object> properties;

    private String remark;

    private Long nodeId;

    private String documents;

    private Long childNumber;

    private AssetEntityView parAssetEntityView;
}
