package com.zysblog.zysblog.controller;
import com.zysblog.zysblog.common.util.CloudApiRequestUtils;
import com.zysblog.zysblog.entity.AppConfig;
import com.zysblog.zysblog.common.api.ResponseWrapper;
import com.zysblog.zysblog.service.AppConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/legendBlog/t-admin")
@Slf4j
public class ConfigController {
    @Autowired
    private AppConfigService appConfigService;
    @PostMapping("/configs")
    public ResponseWrapper<List<AppConfig>> getAllConfigs(){
        return ResponseWrapper.success(appConfigService.getConfigList(), CloudApiRequestUtils.getCloudApiRequest().getRequestId());
    }
}
