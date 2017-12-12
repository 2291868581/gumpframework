package org.gumpframework.web.base.util;

import lombok.extern.slf4j.Slf4j;
import org.gumpframework.util.common.PublicUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 以静态变量保存Spring ApplicatonContext
 */
@Slf4j
public class SpringContextHolder implements ApplicationContextAware,DisposableBean {
    private static ApplicationContext applicationContext = null;
    private static final String contextPath[]={"classpath:applicationContext*.xml"};
    @Override
    public void destroy() throws Exception {
        applicationContext=null;
        SpringContextHolder.applicationContext=null;
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (PublicUtil.isNotEmpty(SpringContextHolder.applicationContext)){
            log.warn("覆盖原有applicationContext:{}"+SpringContextHolder.applicationContext);
        }
        SpringContextHolder.applicationContext = applicationContext;
    }
    private static void assertContextInjected(){
        if (PublicUtil.isEmpty(applicationContext)){
            applicationContext = new ClassPathXmlApplicationContext(contextPath);
        }
    }
}
