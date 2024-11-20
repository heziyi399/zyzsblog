package com.zysblog.zysblog.common.interceptor;

import com.zysblog.zysblog.common.util.JwtInfo;
import com.zysblog.zysblog.common.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final ThreadLocal<JwtInfo> REQUEST_BASE_INFO_THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            // 把业务数据存到 ThreadLocal 中
            REQUEST_BASE_INFO_THREAD_LOCAL.set((JwtInfo) claims);
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

