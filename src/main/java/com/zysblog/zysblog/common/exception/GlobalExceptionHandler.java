package com.zysblog.zysblog.common.exception;

import com.zysblog.zysblog.common.util.CloudApiRequestUtils;
import com.zysblog.zysblog.common.api.CloudApiRequest;
import com.zysblog.zysblog.common.util.JsonUtil;
import com.zysblog.zysblog.common.util.JwtUtil;
import com.zysblog.zysblog.entity.ExceptionLog;
import com.zysblog.zysblog.service.ExceptionLogService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import com.zysblog.zysblog.common.api.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@ResponseBody
@ControllerAdvice({"com.zysblog.zysblog.service"})
public class GlobalExceptionHandler {
    @Autowired
    private ExceptionLogService exceptionLogService;
    @Autowired
    @Qualifier("customThreadPool")
    private ThreadPoolExecutor executor;
    /** 默认异常 */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = Exception.class)
    public ResponseWrapper<Object> handleException(Exception e, HttpServletRequest request) {
        log.error(
                "[Exception] ErrorCode -> {}, RequestURI -> {}，errorMsg->{}",
                CommonErrorCode.INTERNAL_ERROR.getCode(),
                request.getRequestURI(),
                e.getMessage(),
                e);
        saveExceptionLog(request, e.getStackTrace(), e);
        CloudApiRequest cloudApiRequest = CloudApiRequestUtils.getCloudApiRequest();

        return ResponseWrapper.error(
                CommonErrorCode.INTERNAL_ERROR.getCode(),
                CommonErrorCode.INTERNAL_ERROR.getMessage(),
                cloudApiRequest.getRequestId());
    }
    /** 自定义API异常 */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = ApiException.class)
    public ResponseWrapper<Object> handleValidException(
            ApiException e, HttpServletRequest request) {
        log.error(
                "[ApiException] ErrorCode -> {},  RequestURI -> {}",
                e.getCode(),
                request.getRequestURI(),
                e);
        CloudApiRequest cloudApiRequest = CloudApiRequestUtils.getCloudApiRequest();
        saveExceptionLog(request, e.getStackTrace(), e);
        return ResponseWrapper.error(
                e.getCode(), e.getMessage(), cloudApiRequest.getRequestId());
    }

    private void saveExceptionLog(HttpServletRequest request, StackTraceElement[] stackTrace, Exception e) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String param = JsonUtil.toJson(parameterMap);
        String token = request.getHeader("Authorization");
        Map<String, Object> claims = JwtUtil.parseToken(token);
        String uid = (String) claims.get("userId");
        ExceptionLog exceptionLog = new ExceptionLog();
        exceptionLog.setExpRequParam(param);
        exceptionLog.setExceptionMessage(e.getMessage());
        exceptionLog.setOperUri(request.getRequestURI());
        exceptionLog.setStackTrace(Arrays.toString(stackTrace));
        exceptionLog.setOperCreateTime(new Date());
        exceptionLog.setOperUserId(uid);
        executor.execute(() -> exceptionLogService.save(exceptionLog));
    }
}
