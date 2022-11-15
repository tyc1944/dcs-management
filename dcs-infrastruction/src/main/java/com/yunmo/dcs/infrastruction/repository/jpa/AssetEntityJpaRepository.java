package com.yunmo.dcs.infrastruction.repository.jpa;

import com.yunmo.dcs.domain.AssetEntity;
import com.yunmo.dcs.domain.repository.AssetEntityRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/** @author lh */
public interface AssetEntityJpaRepository
    extends JpaRepository<AssetEntity, Long>, AssetEntityRepository {}
