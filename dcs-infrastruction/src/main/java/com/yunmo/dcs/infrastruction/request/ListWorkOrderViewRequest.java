package com.yunmo.dcs.infrastruction.request;

import com.yunmo.dcs.domain.WorkOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.time.LocalDate;

/** @author lh */
@Setter
@Getter
public class ListWorkOrderViewRequest extends BaseSearchRequest {
  @Schema(description = "计划开始时间")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate planStartTime;

  @Schema(description = "工单计划开始时间,时间范围::[开始,结束]")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @Size(min = 1, max = 2)
  private LocalDate[] dateRange;

  private WorkOrder.WorkStatus workStatus;

  @Schema(description = "提交时间")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @Size(min = 1, max = 2)
  private LocalDate[] finishTime;

  @Schema(description = "项目名称")
  private String name;

  private long workerId;
}
