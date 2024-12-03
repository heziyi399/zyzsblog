package com.zysblog.zysblog;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zysblog.zysblog.mapper")
@NacosPropertySource(dataId = "legendblog.zy", autoRefreshed = true)
public class ZysblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZysblogApplication.class, args);
    }

}
