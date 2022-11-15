package com.yunmo.dcs.domain.repository;

import com.yunmo.dcs.domain.UserAccount;
import com.yunmo.domain.common.EntityRepository;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

/** @author lh */
public interface UserAccountRepository extends EntityRepository<UserAccount, Long> {
  Optional<UserAccount> findByAccountName(String phone);

  Optional<UserAccount> findByPhone(String phone);

  Optional<UserAccount> findByPassword(String password);
}
