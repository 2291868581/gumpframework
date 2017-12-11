package com.dai.test.service.client.impl;

import com.dai.test.entity.client.UserInfo;
import com.dai.test.repository.client.UserInfoRepository;
import com.dai.test.service.client.UserInfoService;
import org.gumpframework.domain.bean.PageModel;
import org.gumpframework.repository.base.BaseOrmRepository;
import org.gumpframework.repository.base.BaseQlRepository;
import org.gumpframework.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private BaseOrmRepository baseOrmRepository;
    @Autowired
    private BaseQlRepository baseQlRepository;

    @Override
    public List<Map<String,Object>> login(String name,String password){
        String sql = " SELECT a.name_ AS name,a.password_ AS password,a.id_ AS id from bs_user_info a where a.name_=:p1 and a.password_=:p2 ";
        return baseQlRepository.getListBySQL(sql,name,password);
    }

    @Override
    public PageModel<Map<String,Object>> getPage(PageModel<Map<String,Object>> pm,String name,String password){
        String sql = " SELECT a.name_ AS name,a.password_ AS password,a.id_ AS id from bs_user_info a where a.name_=:p1 and a.password_=:p2 ";
        return (PageModel<Map<String, Object>>) baseQlRepository.getSQLPage(sql,pm,name,password);

    }

}
