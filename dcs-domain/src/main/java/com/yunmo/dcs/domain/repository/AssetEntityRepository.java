package com.yunmo.dcs.domain.repository;

import com.yunmo.dcs.domain.AssetEntity;
import com.yunmo.domain.common.EntityRepository;

import java.util.List;
import java.util.Optional;

/** @author lh */
public interface AssetEntityRepository extends EntityRepository<AssetEntity, Long> {
  List<AssetEntity> findByWorkOrderIdAndEntityType(
      Long WorkOrderId, AssetEntity.EntityType entityType);

  List<AssetEntity> findByParentId(Long parentId);

  List<AssetEntity> findByParentIdIn(List<Long> parentIds);

  Optional<AssetEntity> findTopByParentIdOrderByNodeIdDesc(long parentId);


  //    AssetEntity findByNodeId(Long nodeId);
}
