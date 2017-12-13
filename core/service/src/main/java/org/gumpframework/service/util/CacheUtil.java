package org.gumpframework.service.util;

import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CacheUtil {

    @Resource(name = "redisTemplate")
    ValueOperations<Object, Object> valOpsObj;


}
