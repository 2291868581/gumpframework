package com.dai.test.resource.client;

import com.dai.test.service.client.UserInfoService;
import org.gumpframework.domain.bean.PageModel;
import org.gumpframework.domain.sys.SysUser;
import org.gumpframework.service.sys.SysUserService;
import org.gumpframework.web.base.resource.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "test")
public class UserInfoResource extends BaseResource {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String  test(HttpServletRequest request){
//        UserInfo userInfo = new UserInfo();
//        userInfo.setName("代欣雨");
//        userInfo.setPassword("123456");
        SysUser sysUser = new SysUser();
        sysUser.setName("dddddd");
        sysUser.setPassword("11111");
        sysUserService.save(sysUser);

       return "服务器2 + 8066"+request.getSession().getId();
    }

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    public PageModel<Map<String,Object>> page(){
        PageModel<Map<String,Object>> pm = new PageModel<>();
        return userInfoService.getPage(pm,"代欣雨","123456");
    }
}
