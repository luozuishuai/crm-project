package com.shangma.cn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "com.shangma.cn.mapper")
@EnableAsync
public class ProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class,args);
    }
}
