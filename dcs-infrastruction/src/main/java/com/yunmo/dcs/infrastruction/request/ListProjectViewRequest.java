package com.yunmo.dcs.infrastruction.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Setter
@Getter
public class ListProjectViewRequest extends BaseSearchRequest{
    @Schema(description = "提交时间")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Size(min = 1, max = 2)
    private LocalDate[] finishTime;

    private String projectName;
}
