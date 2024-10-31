package com.zysblog.zysblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zysblog.zysblog.mapper")
public class ZysblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZysblogApplication.class, args);
    }

}
