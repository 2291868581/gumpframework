package org.gumpframework.web.back.resource.sys;


import org.gumpframework.domain.sys.SysUser;
import org.gumpframework.web.base.resource.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.gumpframework.service.sys.SysUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/sysUser")
public class MqResouce extends BaseResource {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public List<Map<String,Object>> index(){
        List<Map<String,Object>>  user = sysUserService.login("代欣雨","123456");
        return user;
    }

    @RequestMapping(value = "/save",method = RequestMethod.GET)
    public String  save(){
        SysUser sysUser = new SysUser();
        sysUser.setName("daixinxyu");
        sysUser.setPassword("111111111");
        sysUserService.save(sysUser);
        return "成功";
    }

}
