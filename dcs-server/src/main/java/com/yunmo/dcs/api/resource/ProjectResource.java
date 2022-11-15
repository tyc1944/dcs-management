package com.yunmo.dcs.api.resource;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yunmo.dcs.domain.Project;
import com.yunmo.dcs.domain.WorkOrder;
import com.yunmo.dcs.domain.repository.ProjectRepository;
import com.yunmo.dcs.domain.value.ProjectDocumentsCreate;
import com.yunmo.dcs.domain.value.ProjectValue;
import com.yunmo.dcs.domain.value.WorkOrderDocumentsCreate;
import com.yunmo.dcs.infrastruction.mapstruct.PageParamMapper;
import com.yunmo.dcs.infrastruction.repository.mybatis.ProjectQueryMapper;
import com.yunmo.dcs.infrastruction.repository.mybatis.WorkOrderQueryMapper;
import com.yunmo.dcs.infrastruction.request.BaseSearchRequest;
import com.yunmo.dcs.infrastruction.request.ListProjectViewRequest;
import com.yunmo.dcs.infrastruction.request.ListWorkOrderViewRequest;
import com.yunmo.dcs.infrastruction.view.ProjectView;
import com.yunmo.dcs.infrastruction.view.WorkOrderView;
import com.yunmo.domain.common.Principal;
import com.yunmo.domain.common.Tenant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.Param;

import javax.validation.groups.Default;
import java.util.List;

/**
 * @author lh
 */
@RestController
@Transactional
@RequestMapping("/api/projects")
@Tag(name = "项目资源")
public class ProjectResource {
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectQueryMapper projectQueryMapper;

    @Autowired
    PageParamMapper pageParamMapper;

    @Autowired
    WorkOrderQueryMapper workOrderQueryMapper;
    @PostMapping
    @Operation(description = "创建项目")
    public Project create(@Principal Tenant tenant, @RequestBody ProjectValue project) {
        return projectRepository.save(project.create());
    }

//    @GetMapping
//    @Operation(description = "项目清单")
//    public List<Project> getProjectList(@Principal Tenant tenant) {
//        return projectRepository.findByCreatedBy(tenant.getId());
//    }

    @GetMapping
    @Operation(description = "根据开始时间或者状态获取列表以及搜索")
    public IPage<ProjectView> getProjectListSearch(
            @Validated(value = {BaseSearchRequest.BaseSearchRequestVaGroup.class, Default.class})
            ListProjectViewRequest listProjectViewRequest) {
        return projectQueryMapper.selectByProjectListPage(
                pageParamMapper.mapper(listProjectViewRequest), listProjectViewRequest);
    }

    @GetMapping("/{id}")
    @Operation(description = "项目详情")
    public ProjectView getProject(@PathVariable("id") Long id) {
        return projectQueryMapper.selectProjectViewById(id);
    }

    @PutMapping("/{id}/documents")
    public Project updateDocuments(
            @PathVariable("id") Project project,
            @RequestBody ProjectDocumentsCreate projectDocumentsCreate) {
        return projectRepository.save(projectDocumentsCreate.assignTo(project));
    }

    @GetMapping("/{projectId}/work/orders")
    @Operation(description = "当前项目搜索列表")
    public IPage<WorkOrderView> getWorkOrderListSearchByProject(@Validated(value = {BaseSearchRequest.BaseSearchRequestVaGroup.class, Default.class}) ListWorkOrderViewRequest listWorkOrderViewRequest,
                                                                @PathVariable("projectId") Long projectId) {
        return workOrderQueryMapper.selectListWorkOrderPageByProject(
                pageParamMapper.mapper(listWorkOrderViewRequest), projectId, listWorkOrderViewRequest);
    }

}
