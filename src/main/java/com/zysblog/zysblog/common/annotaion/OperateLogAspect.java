package com.zysblog.zysblog.common.annotaion;

import com.zysblog.zysblog.common.constants.BaseRedisPrefix;
import com.zysblog.zysblog.common.holder.LocalRequestHolder;
import com.zysblog.zysblog.common.util.*;
import com.zysblog.zysblog.entity.ExceptionLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

@Aspect
@Component
@Slf4j
public class OperateLogAspect {
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 开始时间
     */
    Date startTime;

    @Pointcut(value = "@annotation(operationLog)")
    public void pointcut(OperationLog operationLog) {

    }

    @Around(value = "pointcut(operationLogger)")
    public Object doAround(ProceedingJoinPoint joinPoint, OperationLog operationLogger) throws Throwable {

        startTime = new Date();

        //先执行业务
        Object result = joinPoint.proceed();

        try {
            // 日志收集
            handle(joinPoint);

        } catch (Exception e) {
            log.error("日志记录出错!", e);
        }

        return result;
    }


    @AfterThrowing(value = "pointcut(operationLogger)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, OperationLog operationLogger, Throwable e) throws Exception {

        ExceptionLog exception = new ExceptionLog();
        HttpServletRequest request = LocalRequestHolder.getRequest();
        exception.setOperUri(request.getRequestURI());
        //设置调用的方法
        exception.setMethod(joinPoint.getSignature().getName());

        exception.setStackTrace(Arrays.toString(e.getStackTrace()));
        exception.setExceptionMessage(e.getMessage());
        Map<String, String[]> parameterMap = request.getParameterMap();
        String param = JsonUtil.toJson(parameterMap);
        exception.setExpRequParam(param);
        exception.setOperCreateTime(new Date());
        exception.setExceptionMessage(e.getMessage());
        exception.insert();
    }


    /**
     * 管理员日志收集
     *
     * @param point
     * @throws Exception
     */
    private void handle(ProceedingJoinPoint point) throws Exception {

        HttpServletRequest request = LocalRequestHolder.getRequest();

        Method currentMethod = AspectUtil.getMethod(point);

        //获取操作名称
        OperationLog annotation = currentMethod.getAnnotation(OperationLog.class);

        boolean save = annotation.save();

        String bussinessName = AspectUtil.parseParams(point.getArgs(), annotation.value());


        log.info("{} | {} - {}  - {}", bussinessName, IpUtils.getIpAddr(request), request.getMethod(), request.getRequestURI());
        if (!save) {
            return;
        }

        // 获取参数名称和值
        Map<String, Object> nameAndArgsMap = AspectUtil.getFieldsName(point);;
        // 当前操作用户
        String token = request.getHeader("Authorization");
        Map<String, Object> claims = JwtUtil.parseToken(token);
        String uid = (String) claims.get("userId");
        //String paramsJson = JSONObject.toJSONString(nameAndArgsMap);
        String type = request.getMethod();
        String ip = IpUtils.getIpAddr(request);
        String url = request.getRequestURI();

        //threadPoolTaskExecutor.execute()

        // 异步存储日志
//        threadPoolTaskExecutor.execute(
//                new SysLogHandle(ip, type, url, securityUser,
//                        paramsJson, point.getTarget().getClass().getName(),
//                        point.getSignature().getName(), bussinessName,
//                        startTime, redisUtil));
    }
}
