package com.zysblog.zysblog.controller;



import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zysblog.zysblog.common.api.ResponseWrapper;
import com.zysblog.zysblog.common.interceptor.LoginInterceptor;
import com.zysblog.zysblog.common.util.CloudApi3Util;
import com.zysblog.zysblog.common.util.MailUtils;
import com.zysblog.zysblog.dto.request.RegisterAdminRequest;
import com.zysblog.zysblog.dto.vo.Mail;
import com.zysblog.zysblog.dto.vo.SimpleResult;
import com.zysblog.zysblog.entity.TUser;
import com.zysblog.zysblog.service.TUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.zysblog.zysblog.common.constants.MailConstant.CODE_KEY_PREFIX;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zy
 * @since 2024-11-20
 */
@RestController
@RequestMapping("/t-user")
public class TUserController {
    @Autowired
    private TUserService tUserService;
    @Autowired
    private MailUtils mailUtils;
    @PostMapping("login")
    public ResponseWrapper<SimpleResult> userLogin(@RequestBody TUser tUser){
        SimpleResult simpleResult = new SimpleResult(true);
        return CloudApi3Util.getApi3Response(simpleResult);
    }
    @PostMapping("register")
    public ResponseWrapper<SimpleResult> registerUser(@RequestBody RegisterAdminRequest tUserRequest){
        tUserService.registerUser(tUserRequest);
        SimpleResult simpleResult = new SimpleResult(true);
        return CloudApi3Util.getApi3Response(simpleResult);
    }
}
