package com.yunmo.dcs.infrastruction.service;

import com.yunmo.dcs.domain.UserAccount;
import com.yunmo.dcs.domain.value.UserAccountValue;
import com.yunmo.dcs.domain.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

/** @author lh */
@Service
@Transactional
@RequiredArgsConstructor
public class UserAccountService {
  private final UserAccountRepository userAccountRepository;

  public UserAccount createUserAccount(UserAccountValue userAccountValue) {
    Optional<UserAccount> userAccountOptional =
        userAccountRepository.findByAccountName(userAccountValue.getAccountName());
    if (userAccountOptional.isPresent()) {
      throw new EntityExistsException("账户已存在");
    }

    return userAccountRepository.save(userAccountValue.create());
  }


  public UserAccount changeUserAccount(long userAccountId, UserAccountValue userAccountValue) {
    UserAccount userAccount = getUserAccount(userAccountId);
    return userAccountRepository.save(userAccountValue.patchTo(userAccount));
  }



  public boolean isAdminUserAccount(long userAccountId) {
    return 1L == userAccountId;
  }

  public void switchUserAccount(long userAccountId, boolean enable) {
    UserAccount userAccount = getUserAccount(userAccountId);
    userAccountRepository.save(userAccount.setEnabled(enable));
  }

  public UserAccount getUserAccount(long userAccountId) {
    Optional<UserAccount> userAccountOptional = userAccountRepository.findById(userAccountId);
    if (userAccountOptional.isEmpty()) {
      throw new EntityNotFoundException("账户不存在");
    }
    return userAccountOptional.get();
  }

}
