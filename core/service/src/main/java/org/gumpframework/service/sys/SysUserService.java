package org.gumpframework.service.sys;

import org.gumpframework.domain.sys.SysUser;
import org.gumpframework.service.base.BaseService;
import org.gumpframework.service.base.impl.BaseServiceImpl;

import java.util.List;
import java.util.Map;

public interface SysUserService extends BaseService<SysUser> {
    List<Map<String,Object>> login(String name, String password);
}
