package com.lyf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@MapperScan("com.lyf.dao")
public class DemoApplication extends SpringBootServletInitializer {


    // 打包外面
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(DemoApplication.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
