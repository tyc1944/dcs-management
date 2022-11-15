package com.yunmo.dcs.infrastruction.repository.jdbi;

import com.yunmo.dcs.domain.AssetEntity;
import com.yunmo.generator.annotation.JDBI;

import java.util.List;

@JDBI
public interface AssetEntityQuery {
  List<AssetEntity> findNonemptyChildrenByParents(List<Long> parentIds);
}
