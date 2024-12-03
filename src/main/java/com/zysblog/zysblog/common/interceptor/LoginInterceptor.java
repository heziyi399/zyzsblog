package com.zysblog.zysblog.common.interceptor;

import com.zysblog.zysblog.common.exception.ApiException;
import com.zysblog.zysblog.common.exception.ErrorCode;
import com.zysblog.zysblog.common.util.JsonUtil;
import com.zysblog.zysblog.common.util.JwtInfo;
import com.zysblog.zysblog.common.util.JwtUtil;
import com.zysblog.zysblog.common.util.RedisUtil;
import com.zysblog.zysblog.entity.TUser;
import com.zysblog.zysblog.service.AppConfigService;
import com.zysblog.zysblog.service.TUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.zysblog.zysblog.common.constants.MetaConstants.TOKEN_SURVIVAL_TIME;
import static com.zysblog.zysblog.common.constants.MetaConstants.USER_TOKEN;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    private static final ThreadLocal<JwtInfo> REQUEST_BASE_INFO_THREAD_LOCAL = new ThreadLocal<>();
    public static JwtInfo getRequestUserInfo() {
        JwtInfo requestUserInfo = REQUEST_BASE_INFO_THREAD_LOCAL.get();
        return requestUserInfo;
    }
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AppConfigService configService;
    @Autowired
    private TUserService tUserService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            // 把业务数据存到 ThreadLocal 中
            REQUEST_BASE_INFO_THREAD_LOCAL.set((JwtInfo) claims);
            //续期
            if (redisUtil.get(USER_TOKEN + token) != null) {
                TUser user = tUserService.getById((String)claims.get("userId"));
                redisUtil.setEx(USER_TOKEN+ token, JsonUtil.toJson(user),Long.parseLong(TOKEN_SURVIVAL_TIME), TimeUnit.HOURS);
                return true;
            } else {
                log.warn("token已过期");
            }
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        REQUEST_BASE_INFO_THREAD_LOCAL.remove();
    }
}

