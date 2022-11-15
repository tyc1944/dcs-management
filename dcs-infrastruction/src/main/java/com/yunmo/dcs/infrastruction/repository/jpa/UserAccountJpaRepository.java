package com.yunmo.dcs.infrastruction.repository.jpa;

import com.yunmo.dcs.domain.UserAccount;
import com.yunmo.dcs.domain.repository.UserAccountRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/** @author lh */
public interface UserAccountJpaRepository
    extends JpaRepository<UserAccount, Long>, UserAccountRepository {}
