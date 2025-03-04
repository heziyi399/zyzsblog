package com.zysblog.zysblog.common.holder;

import com.zysblog.zysblog.common.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.zysblog.zysblog.common.exception.ErrorCode;
import static com.zysblog.zysblog.common.constants.MetaConstants.ADMIN_UID;
import org.springframework.util.StringUtils;
@Slf4j
public class LocalRequestHolder {
    /**
     * 获取request
     *
     * @return HttpServletRequest
     */
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
    public static String checkLogin() {
        if (StringUtils.isEmpty(getAdminUid())) {
            throw new ApiException(ErrorCode.INVALID_TOKEN);
        }
        return getAdminUid();
    }
    public static String getAdminUid() {
        HttpServletRequest request = LocalRequestHolder.getRequest();
        if (request.getAttribute(ADMIN_UID) != null) {
            return request.getAttribute(ADMIN_UID).toString();
        } else {
            return null;
        }
    }
}
