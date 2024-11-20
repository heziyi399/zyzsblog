package com.zysblog.zysblog.common.util.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class RequestHolder {
    public static HttpServletRequest getRequest() {
        log.debug("getRequest -- Thread id :{}, name : {}", Thread.currentThread().getId(), Thread.currentThread().getName());
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if (null == servletRequestAttributes) {
            return null;
        }
        return servletRequestAttributes.getRequest();
    }

    /**
     * 获取Response
     *
     * @return HttpServletRequest
     */
    public static HttpServletResponse getResponse() {
        log.debug("getResponse -- Thread id :{}, name : {}", Thread.currentThread().getId(), Thread.currentThread().getName());
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if (null == servletRequestAttributes) {
            return null;
        }
        return servletRequestAttributes.getResponse();
    }

}
