package com.dai.test.resource.client;

import com.dai.test.entity.client.UserInfo;
import com.dai.test.service.client.UserInfoService;
import org.gumpframework.domain.bean.PageModel;
import org.gumpframework.web.base.resource.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "test")
public class UserInfoResource extends BaseResource {
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public List<Map<String,Object>> test(){
//        UserInfo userInfo = new UserInfo();
//        userInfo.setName("代欣雨");
//        userInfo.setPassword("123456");
        return userInfoService.login("代欣雨","123456");
    }

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    public PageModel<Map<String,Object>> page(){
        PageModel<Map<String,Object>> pm = new PageModel<>();
        return userInfoService.getPage(pm,"代欣雨","123456");
    }
}
