package com.zysblog.zysblog.Config;


import com.zysblog.zysblog.common.api.CloudApiRequest;
import com.zysblog.zysblog.common.util.CloudApiRequestUtils;
import com.zysblog.zysblog.common.util.HttpServletRequestUriWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebFilter("/legendBlog")
@Component
public class CloudApiFilter implements Filter {
    public static final String TRACE_ID = "traceId";
    private static final Logger LOG = LoggerFactory.getLogger(CloudApiFilter.class);
    private static final ThreadLocal<CloudApiRequest> REQUEST_BASE_INFO_THREAD_LOCAL =
            new ThreadLocal<>();

    @Override
    public void init(FilterConfig filterConfig) {
        // noting to do
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        if (httpRequest.getRequestURI().contains("upload")) {
            LOG.info("isFile");
            CloudApiRequestUtils.setCloudApiRequest(CloudApiRequestUtils.buildRequestInfo(CloudApiRequestUtils.generateRequestId()));
//
//            // 处理文件上传
//            HttpServletRequestUriWrapper wrapper =
//                    new HttpServletRequestUriWrapper(
//                            httpRequest, httpRequest.getRequestURI(), requestBodyPayload);
//
//            // 将请求传递到下一个过滤器（或者最终到达控制器方法）
            filterChain.doFilter(servletRequest, servletResponse);
            return ;
            }
        String requestBodyPayload =
                StreamUtils.copyToString(servletRequest.getInputStream(), StandardCharsets.UTF_8);
        // 解析Body参数，并存入threadLocal管理
        CloudApiRequest cloudApiRequest =
                CloudApiRequestUtils.getRequestInfoFromReq(requestBodyPayload);

        // 将traceId写入日志变量
        MDC.put(TRACE_ID, cloudApiRequest.getRequestId());
        // 将基础信息写入treadlocal
        CloudApiRequestUtils.setCloudApiRequest(cloudApiRequest);

        // 读取过body，需要重新设置body
        HttpServletRequestUriWrapper wrapper =
                new HttpServletRequestUriWrapper(
                        httpRequest, httpRequest.getRequestURI(), requestBodyPayload);

        // 将请求传递到下一个过滤器（或者最终到达控制器方法）
        filterChain.doFilter(wrapper, servletResponse);
    }

    @Override
    public void destroy() {
        CloudApiRequestUtils.removeCloudApiRequest();
        MDC.clear();
    }
}
