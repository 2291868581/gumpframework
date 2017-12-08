package org.gumpframework.service.sys.impl;

import org.gumpframework.domain.sys.SysUser;
import org.gumpframework.repository.sys.SysUserRepository;
import org.gumpframework.service.base.impl.BaseServiceImpl;
import org.gumpframework.service.sys.SysUserService;
import org.gumpframework.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

    @Override
    public List<Map<String,Object>> login(String name,String password){
        String sql = " SELECT a.name_ AS name,a.password_ AS password,a.id_ AS id from sys_user_t a where a.name_=:p1 and a.password_=:p2 ";
        return baseQlRepository.getListBySQL(sql,name,password);
    }
}
