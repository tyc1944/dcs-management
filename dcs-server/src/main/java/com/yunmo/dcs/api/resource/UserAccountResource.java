package com.yunmo.dcs.api.resource;

import com.yunmo.dcs.api.dto.CheckVerificationPhone;
import com.yunmo.dcs.domain.UserAccount;
import com.yunmo.dcs.domain.value.UserAccountValue;
import com.yunmo.dcs.domain.repository.UserAccountRepository;
import com.yunmo.dcs.infrastruction.service.UserAccountService;
import com.yunmo.dcs.api.dto.ChangePasswordRequest;
import com.yunmo.domain.common.Principal;
import com.yunmo.domain.common.Tenant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.util.List;

/**
 * @author lh
 */
@RestController
@Transactional
@RequestMapping("/api/accounts")
@Tag(name = "账户资源")
public class UserAccountResource {
  @Autowired UserAccountRepository userAccountRepository;
  @Autowired PasswordEncoder passwordEncoder;
  @Autowired UserAccountService userAccountService;
  @Operation(description = "已登录用户信息")
  @GetMapping("/me")
  public UserAccount get(@Principal Tenant tenant) {
    return userAccountRepository
        .findById(tenant.getId())
        .orElseThrow(
            () -> {
              throw Problem.valueOf(Status.NOT_FOUND, "账户信息不存在");
            });
  }

  @PatchMapping("/{id}")
  @Operation(description = "修改用户信息用户信息")
  public void update(
      @PathVariable(value = "id") UserAccount userAccount,
      @RequestBody UserAccountValue userAccountValue) {
    userAccountRepository.save(userAccountValue.patchTo(userAccount));
  }

  @PutMapping("/-:password/{id}")
  @Operation(description = "修改密码(已知密码)")
  public void updatePassword(@Principal Tenant tenant,
                             @Validated @RequestBody ChangePasswordRequest changePasswordRequest){
      UserAccount userAccount = userAccountService.getUserAccount(tenant.getId());
      if (!passwordEncoder.matches(
              changePasswordRequest.getOldPassword(), userAccount.getPassword())) {
          throw new RuntimeException("原密码输入错误，请输入正确的原密码");
      }
    if(changePasswordRequest.getNewPassword().equals(changePasswordRequest.getNewSecondPassword())){
      throw  new RuntimeException("两次密码输入不一致");
    }
      userAccount.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
      userAccountRepository.save(userAccount);
  }

    @PutMapping("/verification/newPhone")
    @Operation(description = "短信验证码验证以及修改手机号(已登录)")
    public UserAccount checkVerificationAndUpdatePhone(@Principal Tenant tenant,@Validated @RequestBody CheckVerificationPhone checkVerificationPhone) {
        UserAccount userAccount = userAccountService.getUserAccount(tenant.getId());
        checkVerificationPhone.check();
        return userAccountRepository.save(
                userAccount.setPhone(checkVerificationPhone.getPhone()));
    }

    @GetMapping("/all")
    @Operation(description = "全部用户")
    public List<UserAccount> getAllUserAccountList(){
        return userAccountRepository.findAll();
    }

    @PostMapping
    public UserAccount create(UserAccountValue userAccountValue){
      return userAccountRepository.save(userAccountValue.create());
    }

    @PutMapping("/{id}")
    public UserAccount update(UserAccountValue userAccountValue, @PathVariable UserAccount userAccount){
        return userAccountRepository.save(userAccountValue.patchTo(userAccount));
    }

}
