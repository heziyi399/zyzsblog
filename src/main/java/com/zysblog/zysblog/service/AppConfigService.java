package com.zysblog.zysblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zysblog.zysblog.entity.AppConfig;
import com.zysblog.zysblog.mapper.AppConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppConfigService {
    @Autowired
    private AppConfigMapper appConfigRepository;

    public String getConfigValue(String key) {
        AppConfig config = appConfigRepository.selectOne(new QueryWrapper<AppConfig>().eq("ConfigKey", key));
        return config != null ? config.getConfigValue() : null;
    }
    public List<AppConfig> getConfigList(){
       return  appConfigRepository.selectList(null);
    }
}
