package com.dai.test;


import org.gumpframework.web.base.config.GumpProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

@ComponentScan(basePackages = {"com.dai.*","org.gumpframework.*"})
@EntityScan(basePackages = { "org.gumpframework.domain.*", "com.dai.test.entity.*"})
@SpringBootApplication
@EnableConfigurationProperties({GumpProperties.class})
@EnableCaching
public class TestStart {

    private static  final Logger logger = LoggerFactory.getLogger(TestStart.class);

    public static void main(String[] args)throws Exception{
            SpringApplication application = new SpringApplication(TestStart.class);
            final ApplicationContext applicationContext = application.run(args);
            Environment environment = applicationContext.getEnvironment();

            logger.info("\n---------------------------------\n\t"
                            +"Application '{}' is running! Access URLS: \n\t "+ "Local: \t\thttp://localhost:{}\n\t"
                            +"External:\thttp://{}:{}\n---------------------------------", environment.getProperty("spring.application.name"),
                    environment.getProperty("server.port"), InetAddress.getLocalHost().getHostAddress(),environment.getProperty("server.port"));

    }

}
