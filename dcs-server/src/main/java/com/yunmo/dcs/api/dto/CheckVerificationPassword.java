package com.yunmo.dcs.api.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckVerificationPassword extends CheckVerification{
    @NotNull(message = "密码不能为空")
    private String password;
}
