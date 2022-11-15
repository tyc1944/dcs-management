package com.yunmo.dcs.infrastruction.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunmo.dcs.domain.AssetEntity;
import com.yunmo.dcs.infrastruction.view.AssetEntityView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/** @author lh */
@Mapper
public interface AssetEntityQueryMapper extends BaseMapper<AssetEntity> {
  List<AssetEntity> findNonEmptyChildrenByParents(@Param("parentIds") List<Long> parentIds);

  List<AssetEntityView> findEntityList(@Param("assetEntitySearch") AssetEntity assetEntitySearch);

  Optional<AssetEntityView> findEntityById(@Param("id") Long id);
  List<AssetEntityView> findMenuByChildId(Long id);
}
