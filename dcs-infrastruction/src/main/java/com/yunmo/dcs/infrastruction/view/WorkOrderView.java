package com.yunmo.dcs.infrastruction.view;

import com.yunmo.dcs.domain.Location;
import com.yunmo.dcs.domain.Project;
import com.yunmo.dcs.domain.UserAccount;
import com.yunmo.dcs.domain.WorkOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Instant;

@Data
public class WorkOrderView{

    @Schema(description = "执行人")
    private UserAccount worker;

    @Schema(description = "发起人")
    private UserAccount createdMan;

    private WorkOrder workOrder;
}
