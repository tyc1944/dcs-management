package com.yunmo.dcs.api.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckVerificationPhone extends CheckVerification{

    @NotNull(message = "手机号不能为空")
    private String Phone;
}
