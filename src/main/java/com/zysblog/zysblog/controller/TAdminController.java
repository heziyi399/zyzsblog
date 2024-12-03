package com.zysblog.zysblog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zysblog.zysblog.common.api.ResponseWrapper;
import com.zysblog.zysblog.common.exception.ApiException;
import com.zysblog.zysblog.common.exception.ErrorCode;
import com.zysblog.zysblog.common.util.*;
import com.zysblog.zysblog.common.util.web.RequestHolder;
import com.zysblog.zysblog.dto.request.EmailRequest;
import com.zysblog.zysblog.dto.request.LoginAdminRequest;
import com.zysblog.zysblog.dto.request.RegisterAdminRequest;
import com.zysblog.zysblog.dto.vo.Mail;
import com.zysblog.zysblog.dto.vo.SimpleResult;
import com.zysblog.zysblog.entity.TAdmin;
import com.zysblog.zysblog.service.AppConfigService;
import com.zysblog.zysblog.service.TAdminService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.zysblog.zysblog.common.constants.MetaConstants.TOKEN_SURVIVAL_TIME;
import static com.zysblog.zysblog.common.constants.MetaConstants.USER_TOKEN;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author zy
 * @since 2024-10-30
 */
@RestController
@RequestMapping("/legendBlog/t-admin")
@Slf4j
public class TAdminController {
    @Autowired
    private TAdminService tAdminService;
    @Autowired
    private MailUtils mailUtils;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AppConfigService configService;

    @GetMapping(value = "/hello")
    public String hello() {
        return "i am swaagger";
    }

    @GetMapping("/register")
    public ResponseWrapper<SimpleResult> registerAdmin(@RequestBody RegisterAdminRequest request, HttpServletRequest servletRequest) {
        SimpleResult simpleResult = new SimpleResult(true);
        TAdmin admin = new TAdmin();
        admin.setLastLoginIp(servletRequest.getRemoteAddr());
        BeanUtils.copyProperties(request, admin);
        tAdminService.save(admin);
        return CloudApi3Util.getApi3Response(simpleResult);
    }

    @PostMapping("/sendMail")
    public ResponseWrapper<SimpleResult> sendMain(@RequestBody EmailRequest request) {
        Mail mail = new Mail();
        mail.setTo(request.getEmail());
        mailUtils.sendVerificationCode(mail);
        SimpleResult simpleResult = new SimpleResult(true);
        return CloudApi3Util.getApi3Response(simpleResult);
    }

    @PostMapping("/login")
    public ResponseWrapper<String> loginAdmin(@RequestBody LoginAdminRequest adminRequest) {
        QueryWrapper<TAdmin> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(wrapper -> wrapper.eq("userName", adminRequest.getUserName()));
        TAdmin user = tAdminService.getOne(queryWrapper);
        if (user == null || user.getStatus().equals(false)) {
            throw new ApiException(ErrorCode.ILLEGAL_PARAMETERS, "用户不可用");
        }
        if (StringUtils.isNotEmpty(user.getPassWord()) && user.getPassWord().equals(MD5Utils.string2MD5(adminRequest.getPassWord()))) {
            // 更新登录信息
            HttpServletRequest request = RequestHolder.getRequest();
            String ip = IpUtils.getIpAddr(request);
            Map<String, String> userMap = IpUtils.getOsAndBrowserInfo(request);
            user.setLastLoginIp(ip);
            user.setLastLoginTime(new Date());
            // 登录成功后，次数+1
            Integer count = user.getLoginCount() + 1;
            user.setLoginCount(count);
            user.updateById();
            // 获取用户头像
            if (!StringUtils.isEmpty(user.getAvatar())) {
//                String avatarResult = pictureFeignClient.getPicture(user.getAvatar(), ",");
//                List<String> picList = webUtil.getPicture(avatarResult);
//                if (picList != null && picList.size() > 0) {
//                    user.setPhotoUrl(webUtil.getPicture(avatarResult).get(0));
//                }
            }
            // 生成token
            Map<String, Object> claims = new HashMap<>();
            claims.put("username", user.getUserName());
            claims.put("userId",user.getUid());
            claims.put("password", user.getPassWord());
            String token = JwtUtil.genToken(claims);
            // 过滤密码
            user.setPassWord("");
            //将从数据库查询的数据缓存到redis中
            redisUtil.setEx(configService.getConfigValue(USER_TOKEN) + token, JsonUtil.toJson(user), Long.parseLong(TOKEN_SURVIVAL_TIME), TimeUnit.HOURS);
            log.info("登录成功，返回token: ", token);
            return CloudApi3Util.getApi3Response(token);
        }
        return CloudApi3Util.getApi3Response(null);
    }


}
