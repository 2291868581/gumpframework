package org.gumpframework.repository.sys;

import org.gumpframework.domain.sys.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * 后台管理用户Dao
 */
public interface SysUserRepository extends JpaRepository<SysUser,String>,JpaSpecificationExecutor<SysUser>{
    Optional<SysUser> findOneById(String id);
}
