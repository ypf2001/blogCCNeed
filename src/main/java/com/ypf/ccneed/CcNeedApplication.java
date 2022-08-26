package com.ypf.ccneed;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @program: ccneed
 * @author: yanpengfan
 * @create: 2022-07-27 17:18
 **/
@EnableCaching
@SpringBootApplication
@MapperScan("com.ypf.ccneed.mapper")
public class CcNeedApplication {
    public static void main(String[] args) {
        SpringApplication.run(CcNeedApplication.class,args);
    }
}
