package com.yunmo.dcs.infrastruction.repository.jpa;

import com.yunmo.dcs.domain.WorkOrder;
import com.yunmo.dcs.domain.repository.WorkOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/** @author lh */
public interface WorkOrderJpaRepository
    extends JpaRepository<WorkOrder, Long>, WorkOrderRepository {}
