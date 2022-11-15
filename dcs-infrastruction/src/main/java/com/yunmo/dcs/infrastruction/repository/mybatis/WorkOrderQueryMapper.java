package com.yunmo.dcs.infrastruction.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.dcs.domain.WorkOrder;
import com.yunmo.dcs.infrastruction.request.BaseSearchRequest;
import com.yunmo.dcs.infrastruction.request.ListWorkOrderViewRequest;
import com.yunmo.dcs.infrastruction.view.WorkOrderView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/** @author lh */
@Mapper
public interface WorkOrderQueryMapper extends BaseMapper<WorkOrder> {
  IPage<WorkOrder> selectListWorkOrderPage(
      @Param("page") Page<?> page,
      @Param("listWorkOrderViewRequest") ListWorkOrderViewRequest listWorkOrderViewRequest);

  IPage<WorkOrderView> selectListWorkOrderPageByProject(
          @Param("page") Page<?> page,
          @Param("projectId") long projectId,
          @Param("listWorkOrderViewRequest") ListWorkOrderViewRequest listWorkOrderViewRequest);

//  IPage<WorkOrderView> selectListWorkOrderPageByType(
//          @Param("page") Page<?> page,
//          @Param("listWorkOrderViewRequest") ListWorkOrderViewRequest listWorkOrderViewRequest);

  WorkOrderView selectWorkerOrderById(@Param("id") Long id);

  WorkOrderView selectWorkerByProjectId(@Param("id") Long id);
}
