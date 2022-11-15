package com.yunmo.dcs.api.resource;

import com.yunmo.dcs.domain.UserAccount;
import com.yunmo.dcs.domain.repository.UserAccountRepository;
import com.yunmo.dcs.infrastruction.config.MessageType;
import com.yunmo.dcs.infrastruction.service.UserAccountService;
import com.yunmo.dcs.infrastruction.service.YunPianUnit;
import com.yunmo.dcs.api.dto.CheckVerificationPassword;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@RestController
@Transactional
@RequestMapping("/public/sms/{phone}")
@Tag(name = "用户登录相关")
public class VerificationResource {

    @Autowired
    UserAccountRepository userAccountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired UserAccountService userAccountService;

    @PostMapping("/message")
    @Operation(description = "发送短信验证码")
    public Map<String, Object> getVerification(@PathVariable("phone") String phone, MessageType messageType) {
        if(messageType==MessageType.forgetMessage) {
            userAccountRepository.findByPhone(phone).orElseThrow(() -> new RuntimeException("手机号不存在"));
        }
        String verification = "";
        Random r = new Random();
        for (int a = 1; a <= 4; a++) {
            verification += r.nextInt(10);
        }
        YunPianUnit.sendMessage(verification, phone, messageType);
        //System.out.println(verification);
        Instant sendTime = Instant.now();
        String verificationMD5 = DigestUtils.md5DigestAsHex(verification.getBytes());
        Map<String, Object> dateAndVerification = new HashMap<>();
        dateAndVerification.put("sendTime", sendTime);
        dateAndVerification.put("verificationMD5", verificationMD5);
        return dateAndVerification;
    }

    @PutMapping("/verification/newPassword")
    @Operation(description = "短信验证码验证以及修改密码(忘记密码)")
    public UserAccount checkVerificationAndUpdatePassword(@Validated @RequestBody CheckVerificationPassword checkVerificationPassword,
                                                          @PathVariable("phone") String phone) {
        checkVerificationPassword.check();
        String newPassword = passwordEncoder.encode(checkVerificationPassword.getPassword());
        return userAccountRepository.save(
                userAccountRepository
                        .findByPhone(phone)
                        .orElseThrow(() -> new RuntimeException("手机号不存在"))
                        .setPassword(newPassword));
    }

}



