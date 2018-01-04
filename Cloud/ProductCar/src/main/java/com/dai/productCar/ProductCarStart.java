package com.dai.productCar;


import org.gumpframework.web.base.config.GumpProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;

@ComponentScan(basePackages = {"com.dai.*","org.gumpframework.*"})
@EntityScan(basePackages = { "org.gumpframework.domain.*", "com.dai.test.entity.*"})
@SpringBootApplication
@EnableConfigurationProperties({GumpProperties.class})
@EnableEurekaClient
public class ProductCarStart {

    private static  final Logger logger = LoggerFactory.getLogger(ProductCarStart.class);

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args)throws Exception{
            SpringApplication application = new SpringApplication(ProductCarStart.class);
            final ApplicationContext applicationContext = application.run(args);
            Environment environment = applicationContext.getEnvironment();

            logger.info("\n---------------------------------\n\t"
                            +"Application '{}' is running! Access URLS: \n\t "+ "Local: \t\thttp://localhost:{}\n\t"
                            +"External:\thttp://{}:{}\n---------------------------------", environment.getProperty("spring.application.name"),
                    environment.getProperty("server.port"), InetAddress.getLocalHost().getHostAddress(),environment.getProperty("server.port"));

    }



}
