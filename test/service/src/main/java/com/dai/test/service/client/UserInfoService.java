package com.dai.test.service.client;

import com.dai.test.entity.client.UserInfo;
import org.gumpframework.domain.bean.PageModel;
import org.gumpframework.service.base.BaseService;

import java.util.List;
import java.util.Map;

public interface UserInfoService extends BaseService<UserInfo> {
    List<Map<String,Object>> login(String name, String password);
    PageModel<Map<String,Object>> getPage(PageModel<Map<String,Object>> pm, String name, String password);
}
