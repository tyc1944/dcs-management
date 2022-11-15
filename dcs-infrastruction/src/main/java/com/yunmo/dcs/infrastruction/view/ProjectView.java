package com.yunmo.dcs.infrastruction.view;

import com.yunmo.dcs.domain.Location;
import com.yunmo.dcs.domain.UserAccount;
import com.yunmo.dcs.domain.WorkOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectView {
    private Long id;

    private String name;

    @Schema(description = "发起时间")
    private Instant createdDate;

//    @Schema(description = "最新提交时间")
//    private Instant newFinishTime;

    @Schema(description = "任务数")
    private Long workCount;

    @Schema(description = "发起人")
    private UserAccount createdMan;

    @Schema(description = "最新提交记录")
    private WorkOrderView newSubmitWorkOrder;

    private Location location;

    private String content;

    private String documents;
}
