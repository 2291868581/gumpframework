package com.dai.test.service.client.impl;

import com.dai.test.entity.client.UserInfo;
import com.dai.test.repository.client.UserInfoRepository;
import com.dai.test.service.client.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.gumpframework.domain.bean.PageModel;
import org.gumpframework.repository.base.BaseOrmRepository;
import org.gumpframework.repository.base.BaseQlRepository;
import org.gumpframework.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
@CacheConfig(cacheNames = "userInfo")
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {

    @Autowired
    private BaseQlRepository baseQlRepository;

    @Cacheable(key = "#p0")
    @Override
    public List<Map<String,Object>> login(@Param("name") String name, String password){
        String sql = " SELECT a.name_ AS name,a.password_ AS password,a.id_ AS id from bs_user_info a where a.name_=:p1 and a.password_=:p2 ";
        return baseQlRepository.getListBySQL(sql,name,password);
    }

    @Override
    public PageModel<Map<String,Object>> getPage(PageModel<Map<String,Object>> pm,String name,String password){
        String sql = " SELECT a.name_ AS name,a.password_ AS password,a.id_ AS id from bs_user_info a where a.name_=:p1 and a.password_=:p2 ";
        return (PageModel<Map<String, Object>>) baseQlRepository.getSQLPage(sql,pm,name,password);

    }



}
