package com.yunmo.dcs.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.DigestUtils;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckVerification {
    @NotNull(message = "发送时间不能为空")
    private Instant sendTime;

    @NotNull(message = "验证码不能为空")
    private String verification;

    private String verificationMD5;

    public void check(){
        if (!(DigestUtils.md5DigestAsHex(verification.getBytes()).equals(verificationMD5))){
            throw new RuntimeException("验证码错误");
        }
        if (Duration.between(sendTime, Instant.now()).toSeconds()>300) {
            throw new RuntimeException("已超时，请重新发送");
        }
    }
}
