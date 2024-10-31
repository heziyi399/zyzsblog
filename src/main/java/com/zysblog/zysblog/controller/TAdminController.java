package com.zysblog.zysblog.controller;


import com.zysblog.zysblog.common.api.ResponseWrapper;
import com.zysblog.zysblog.common.util.CloudApi3Util;
import com.zysblog.zysblog.dto.request.RegisterAdminRequest;
import com.zysblog.zysblog.dto.vo.SimpleResult;
import com.zysblog.zysblog.entity.TAdmin;
import com.zysblog.zysblog.service.TAdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author zy
 * @since 2024-10-30
 */
@RestController
@RequestMapping("/t-admin")
public class TAdminController {
    @Autowired
    private TAdminService tAdminService;
    @GetMapping(value = "/hello")
    public String hello() {
        return "i am swaagger";
    }

    @GetMapping("/register")
    public ResponseWrapper<SimpleResult> registerAdmin(@RequestBody RegisterAdminRequest request, HttpServletRequest servletRequest) {
        SimpleResult simpleResult = new SimpleResult(true);
        TAdmin admin = new TAdmin();
        admin.setLastLoginIp(servletRequest.getRemoteAddr());
        BeanUtils.copyProperties(request,admin);
        tAdminService.save(admin);
        return CloudApi3Util.getApi3Response(simpleResult);
    }
}
