package com.zysblog.zysblog.Config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CosConfig {

    //@Value("${tencent.cos.secretId}")
    @NacosValue(value = "${secretId}", autoRefreshed = true)
    private String secretId;

    //@Value("${tencent.cos.secretKey}")
    @NacosValue(value = "${secretKey}", autoRefreshed = true)
    private String secretKey;

    @Value("${tencent.cos.region}")
    private String region;

    @Bean
    public COSClient cosClient() {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        log.info("client:{},id:{}",secretId,secretKey);
        return new COSClient(cred, clientConfig);
    }
}
