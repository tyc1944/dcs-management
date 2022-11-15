package com.yunmo.dcs.infrastruction.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunmo.dcs.domain.AssetEntity;
import com.yunmo.dcs.domain.UserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.h2.engine.User;

@Mapper
public interface UserAccountQueryMapper  extends BaseMapper<UserAccount> {
}
