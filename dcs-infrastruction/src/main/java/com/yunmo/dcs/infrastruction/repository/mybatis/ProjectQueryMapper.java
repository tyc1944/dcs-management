package com.yunmo.dcs.infrastruction.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.dcs.domain.AssetEntity;
import com.yunmo.dcs.domain.Project;
import com.yunmo.dcs.domain.WorkOrder;
import com.yunmo.dcs.infrastruction.request.ListProjectViewRequest;
import com.yunmo.dcs.infrastruction.request.ListWorkOrderViewRequest;
import com.yunmo.dcs.infrastruction.view.ProjectView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author lh
 */
@Mapper
public interface ProjectQueryMapper extends BaseMapper<Project> {
  Project selectByProjectId(@Param("projectId") Long projectId);

  IPage<ProjectView> selectByProjectListPage(
          @Param("page") Page<?> page,
          @Param("listProjectViewRequest") ListProjectViewRequest listProjectViewRequest);

  ProjectView selectProjectViewById(@Param("id") Long Id);

}
