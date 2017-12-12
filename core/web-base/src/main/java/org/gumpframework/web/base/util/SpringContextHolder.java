package org.gumpframework.web.base.util;

import lombok.extern.slf4j.Slf4j;
import org.gumpframework.util.common.PublicUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 以静态变量保存Spring ApplicatonContext
 */
@Slf4j
public class SpringContextHolder implements ApplicationContextAware,DisposableBean {
    private static ApplicationContext applicationContext = null;

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (PublicUtil.isNotEmpty(applicationContext)){
            
        }
    }

}
