package com.yunmo.dcs.api.resource;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yunmo.dcs.domain.WorkOrder;
import com.yunmo.dcs.domain.value.WorkOrderDocumentsCreate;
import com.yunmo.dcs.domain.value.WorkOrderValue;
import com.yunmo.dcs.domain.repository.AssetEntityRepository;
import com.yunmo.dcs.domain.repository.ProjectRepository;
import com.yunmo.dcs.domain.repository.WorkOrderRepository;
import com.yunmo.dcs.infrastruction.mapstruct.PageParamMapper;
import com.yunmo.dcs.infrastruction.repository.mybatis.WorkOrderQueryMapper;
import com.yunmo.dcs.infrastruction.request.BaseSearchRequest;
import com.yunmo.dcs.infrastruction.request.ListWorkOrderViewRequest;
import com.yunmo.dcs.infrastruction.view.WorkOrderView;
import com.yunmo.domain.common.Principal;
import com.yunmo.domain.common.Tenant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.time.Instant;

import static com.yunmo.dcs.domain.WorkOrder.WorkStatus.FINISHED;

/**
 * @author lh
 */
@RestController
@Transactional
@RequestMapping("/api/work/orders")
@Tag(name = "工单资源")
public class WorkOrderResource {
    @Autowired
    WorkOrderRepository workOrderRepository;

    @Autowired
    AssetEntityRepository assetEntityRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    WorkOrderQueryMapper workOrderQueryMapper;

    @Autowired
    PageParamMapper pageParamMapper;

//    @GetMapping("/{id}")
//    public WorkOrder getById(@PathVariable(value = "id") WorkOrder workOrder) {
//        return workOrder;
//    }

    @GetMapping("/{id}")
    @Operation(description = "任务详情")
    public WorkOrderView getWorOrderById(@PathVariable("id") Long id) {
        return workOrderQueryMapper.selectWorkerOrderById(id);
    }


    @PutMapping("/{id}/documents")
    public WorkOrder updateDocuments(
            @PathVariable("id") WorkOrder workOrder,
            @RequestBody WorkOrderDocumentsCreate workOrderDocumentsCreate) {
        return workOrderRepository.save(workOrderDocumentsCreate.assignTo(workOrder));
    }

//    @PostMapping
//    public WorkOrder create(@Principal Tenant tenant, @RequestBody WorkOrderValue value) {
//        WorkOrder workOrder = value.create();
//        return workOrderRepository.save(workOrder);
//    }

    @PatchMapping("/{id}")
    public WorkOrder patch(
            @PathVariable("id") WorkOrder workOrder, @RequestBody WorkOrderValue value) {
        workOrder = value.patchTo(workOrder);

        if (value.getWorkStatus() == FINISHED) {
            workOrder.setFinishTime(Instant.now());
        }
        return workOrderRepository.save(workOrder);
    }

    @PostMapping
    public WorkOrder create(@Principal Tenant tenant, @RequestBody WorkOrderValue value) {
        WorkOrder workOrder = value.create();
        return workOrderRepository.save(workOrder);
    }
    @GetMapping
    @Operation(description = "根据开始时间或者状态获取列表以及搜索")
    public IPage<WorkOrder> getWorkOrderListSearch(
            @Principal Tenant tenant, @Validated(value = {BaseSearchRequest.BaseSearchRequestVaGroup.class, Default.class}) ListWorkOrderViewRequest listWorkOrderViewRequest) {
        return workOrderQueryMapper.selectListWorkOrderPage(
                pageParamMapper.mapper(listWorkOrderViewRequest), listWorkOrderViewRequest);
    }


//    @GetMapping("/type")
//    @Operation(description = "类型搜索列表")
//    public IPage<WorkOrderView> getWorkOrderListSearchByType(
//            @Validated(value = {BaseSearchRequest.BaseSearchRequestVaGroup.class, Default.class}) ListWorkOrderViewRequest listWorkOrderViewRequest) {
//        return workOrderQueryMapper.selectListWorkOrderPageByType(
//                pageParamMapper.mapper(listWorkOrderViewRequest), listWorkOrderViewRequest);
//    }

}
