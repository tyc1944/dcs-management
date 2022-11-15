package com.yunmo.dcs.api.resource;

import com.yunmo.dcs.domain.AssetEntity;
import com.yunmo.dcs.domain.WorkOrder;
import com.yunmo.dcs.domain.value.AssetEntityDocumentsCreate;
import com.yunmo.dcs.domain.value.AssetEntityNameCreate;
import com.yunmo.dcs.domain.value.AssetEntitySearch;
import com.yunmo.dcs.domain.value.AssetEntityValue;
import com.yunmo.dcs.infrastruction.repository.jpa.AssetEntityJpaRepository;
import com.yunmo.dcs.infrastruction.repository.mybatis.AssetEntityQueryMapper;
import com.yunmo.dcs.infrastruction.view.AssetEntityView;
import com.yunmo.domain.common.Principal;
import com.yunmo.domain.common.Tenant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@Transactional
@RequestMapping("/api/work/orders/{workId}/entities")
@Tag(name = "工单下资产实体")
public class WorkAssetEntityResource {

  @Autowired AssetEntityJpaRepository assetEntityRepository;
  @Autowired AssetEntityQueryMapper assetEntityQueryMapper;

  @GetMapping
  public List<AssetEntityView> getAssetEntityList(
      @Principal Tenant tenant,
      @PathVariable("workId") WorkOrder workOrder,
      @ModelAttribute AssetEntitySearch assetEntitySearch) {
    return assetEntityQueryMapper.findEntityList(
        assetEntitySearch.create().setWorkOrderId(workOrder.getId()));
  }

  @GetMapping("/{id}")
  public AssetEntityView getAssetEntity(
          @PathVariable("workId") WorkOrder workOrder,
          @PathVariable("id") AssetEntity assetEntity){
      return assetEntityQueryMapper.findEntityById(assetEntity.getId()).orElseThrow(()->new RuntimeException("设备不存在"));
  }


  @PostMapping(value = {"/{id}", ""})
  public AssetEntity create(
      @PathVariable("workId") WorkOrder workOrder,
      @PathVariable("id") Optional<AssetEntity> parent,
      @Principal Tenant tenant,
      @RequestBody AssetEntityValue value) {
    long parentId = parent.map(AssetEntity::getId).orElse(0L);
    Optional<AssetEntity> maxChildNode =
        assetEntityRepository.findTopByParentIdOrderByNodeIdDesc(parentId);

    var entity = value.create().setParentId(parentId);
    entity.setNodeId(entity.createNodeId(parent, maxChildNode)).setWorkOrderId(workOrder.getId());
    return assetEntityRepository.save(entity);
  }

  @PutMapping("/{id}/documents")
  public void updateDocuments(
      @Parameter(description = "工单id") @PathVariable("workId") WorkOrder workOrder,
      @Parameter(description = "资源实体id") @PathVariable("id") AssetEntity assetEntity,
      @RequestBody AssetEntityDocumentsCreate assetEntityDocumentsCreate) {
    assetEntityRepository.save(assetEntityDocumentsCreate.assignTo(assetEntity));
  }

  @DeleteMapping
  public void deleteList(
      @PathVariable("workId") WorkOrder workOrder,
      @Principal Tenant tenant,
      @RequestParam List<Long> ids) {

    if (!assetEntityQueryMapper.findNonEmptyChildrenByParents(ids).isEmpty()) {
      throw new RuntimeException("子目录存在数据,禁止删除！！！");
    }

    assetEntityRepository.deleteAllById(ids);
  }

  @PatchMapping("/{id}/name")
  @Operation(description="修改任务名字")
  public void updateName(
          @PathVariable("workId") WorkOrder workOrder,
          @PathVariable("id") AssetEntity assetEntity,
          @RequestBody AssetEntityNameCreate assetEntityNameCreate){
    assetEntityRepository.save(assetEntityNameCreate.assignTo(assetEntity));
  }

  @Schema(description = "修改子资源实体")
  @PutMapping(value = {"/{id}"})
  public AssetEntity update(
      @Principal Tenant tenant,
      @PathVariable("workId") WorkOrder workOrder,
      @PathVariable("id") AssetEntity entity,
      @Valid @RequestBody AssetEntityValue value) {
    boolean parentChanged = !entity.getParentId().equals(value.getParentId());
    if (parentChanged) {
      long newParentId = value.getParentId();
      AssetEntity newParent = assetEntityRepository.getById(newParentId);

      Optional<AssetEntity> maxChildNode =
          assetEntityRepository.findTopByParentIdOrderByNodeIdDesc(newParentId);

      value.assignTo(entity);
      entity
          .setNodeId(entity.createNodeId(Optional.of(newParent), maxChildNode))
          .setWorkOrderId(workOrder.getId());
      return assetEntityRepository.save(entity);
    }

    value.assignTo(entity).setWorkOrderId(workOrder.getId());
    return assetEntityRepository.save(entity);
  }
}
