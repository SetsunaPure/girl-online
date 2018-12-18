package com.girl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ServletComponentScan
@EnableTransactionManagement
@EnableConfigurationProperties
@SpringBootApplication
//@ComponentScan(basePackages = {"com.girl.core.mapper"})
@ComponentScan(basePackages= {"com.girl"})
//@MapperScan({"com.girl.core.mapper"})
public class GirlApplication {
    public static void main(String[] args) {

        SpringApplication.run(GirlApplication.class, args);
    }
}