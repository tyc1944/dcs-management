package com.yunmo.dcs.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
public class ChangePasswordRequest {
    @NotNull(message = "旧密码不能为空")
    private String oldPassword;
    @NotNull(message = "新密码不能为空")
    private String newPassword;
    private String newSecondPassword;
}
