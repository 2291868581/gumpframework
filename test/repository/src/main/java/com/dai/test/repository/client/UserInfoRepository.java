package com.dai.test.repository.client;

import com.dai.test.entity.client.UserInfo;
import org.gumpframework.domain.sys.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * 客户端用户Repository
 */
public interface UserInfoRepository extends JpaRepository<UserInfo,String>,JpaSpecificationExecutor<UserInfo>{
    Optional<UserInfo> findOneById(String id);
}
