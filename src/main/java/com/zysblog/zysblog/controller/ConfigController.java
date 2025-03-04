package com.zysblog.zysblog.controller;
import com.zysblog.zysblog.common.util.CloudApi3Util;
import com.zysblog.zysblog.common.util.CloudApiRequestUtils;
import com.zysblog.zysblog.dto.request.AddConfigRequest;
import com.zysblog.zysblog.dto.vo.SimpleResult;
import com.zysblog.zysblog.entity.AppConfig;
import com.zysblog.zysblog.common.api.ResponseWrapper;
import com.zysblog.zysblog.service.AppConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/addConfig")
    public ResponseWrapper<SimpleResult> addConfig(@RequestBody AddConfigRequest request){
        AppConfig config = new AppConfig();
        config.setConfigKey(request.getKey());
        config.setConfigValue(request.getValue());
        config.insert();
        SimpleResult res = new SimpleResult();
        res.setResult(true);
        return CloudApi3Util.getApi3Response(res);
    }

}
