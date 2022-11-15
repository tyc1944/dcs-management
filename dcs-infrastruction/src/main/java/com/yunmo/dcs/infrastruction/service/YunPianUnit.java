package com.yunmo.dcs.infrastruction.service;

import com.alibaba.fastjson.JSONObject;
import com.yunmo.dcs.infrastruction.config.MessageType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**云片网发送短信验证码服务接口*/
@Component
public class YunPianUnit {
    public static void sendMessage(String verification, String phone, MessageType messageType){
        String forgetMessage = "【中科云墨】您正在进行账号登录校验，验证码"+verification+"，切勿将验证码泄露于他人，本条验证码有效期5分钟。";
        String updateMessage = "【中科云墨】您正在更换登录手机号码，验证码"+verification+"，切勿将验证码泄露于他人，本条验证码有效期5分钟。";
        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("apikey", "327b14456f23e89485b53c3fe4d1b200");
        if(messageType==MessageType.forgetMessage){
            params.add("text", forgetMessage);
        }else{
            params.add("text", updateMessage);
        }
        params.add("mobile", phone);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/x-www-form-urlencoded;charset=utf-8;"));
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
        RestTemplate client = new RestTemplate();
        JSONObject returnResult = client.postForEntity("https://sms.yunpian.com/v2/sms/single_send.json", httpEntity, JSONObject.class).getBody();
        if (returnResult.getInteger("code") != 0) {
            throw new RuntimeException("短信发送失败");
        }
    }
}
