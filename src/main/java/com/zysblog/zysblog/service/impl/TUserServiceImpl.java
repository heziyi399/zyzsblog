package com.zysblog.zysblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zysblog.zysblog.common.exception.ApiException;
import com.zysblog.zysblog.common.exception.ErrorCode;
import com.zysblog.zysblog.common.util.*;
import com.zysblog.zysblog.common.util.web.RequestHolder;
import com.zysblog.zysblog.dto.request.RegisterAdminRequest;
import com.zysblog.zysblog.entity.TAdmin;
import com.zysblog.zysblog.entity.TUser;
import com.zysblog.zysblog.mapper.TUserMapper;
import com.zysblog.zysblog.service.AppConfigService;
import com.zysblog.zysblog.service.TUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.zysblog.zysblog.common.constants.MailConstant.CODE_KEY_PREFIX;
import static com.zysblog.zysblog.common.constants.MetaConstants.TOKEN_SURVIVAL_TIME;
import static com.zysblog.zysblog.common.constants.MetaConstants.USER_TOKEN;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zy
 * @since 2024-11-20
 */
@Service
@Slf4j
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AppConfigService configService;

    @Override
    public String userLogin(TUser tUserRequest) {
        QueryWrapper<TUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(wrapper -> wrapper.eq("email", tUserRequest.getEmail()));
        TUser user = this.getOne(queryWrapper);
        Map<String, Object> claims = new HashMap<>();
        if(StringUtils.isNotBlank(tUserRequest.getEmail()) && StringUtils.isNotBlank(tUserRequest.getValidCode()))  {
            //验证码登录
            String code = redisUtil.get(CODE_KEY_PREFIX + tUserRequest.getEmail());
            if(code != null && code.equals(tUserRequest.getValidCode())) {
                return geneToken(user,claims);
            }

        }else{
            if(StringUtils.isNotBlank(tUserRequest.getEmail()) && StringUtils.isNotBlank(tUserRequest.getPassWord()) &&
                    user.getPassWord().equals(MD5Utils.string2MD5(tUserRequest.getPassWord()))){
                //邮箱密码登录
                return geneToken(user,claims);
            }
        }
        return null;
    }

    @Override
    public void registerUser(RegisterAdminRequest tUserRequest) {
        try {
            String key = CODE_KEY_PREFIX + tUserRequest.getEmail();
            String value = redisUtil.get(key);
            if (value != null && value.equals(tUserRequest.getValidCode())) {
                TUser user = new TUser();
                BeanUtils.copyProperties(tUserRequest, user);
                user.setPassWord(MD5Utils.string2MD5(tUserRequest.getPassWord()));
                HttpServletRequest request = RequestHolder.getRequest();
                String ip = IpUtils.getIpAddr(request);
                user.setLastLoginIp(ip);
                this.save(user);
            }
        }catch (Exception e){
            log.error("registerUserError:{}",e.getStackTrace());
            throw new ApiException(ErrorCode.INTERNAL_ERROR,e.getMessage());
        }
    }

    private String geneToken(TUser user,Map<String, Object> claims) {
        HttpServletRequest request = RequestHolder.getRequest();
        String ip = IpUtils.getIpAddr(request);
        user.setLastLoginIp(ip);
        user.setLastLoginTime(new Date());
        // 登录成功后，次数+1
        Integer count = user.getLoginCount() + 1;
        user.setLoginCount(count);
        user.updateById();
        claims.put("username", user.getUserName());
        claims.put("userId",user.getUid());
        claims.put("password", user.getPassWord());
        String token = JwtUtil.genToken(claims);
        redisUtil.setEx(configService.getConfigValue(USER_TOKEN) + token, JsonUtil.toJson(user), Long.parseLong(TOKEN_SURVIVAL_TIME), TimeUnit.HOURS);
        return token;
    }
}
