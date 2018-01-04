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
    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public List<Map<String, Object>> login(String name, String password) {
        return null;
    }
}
