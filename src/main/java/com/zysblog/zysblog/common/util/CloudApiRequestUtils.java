package com.zysblog.zysblog.common.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.zysblog.zysblog.common.api.CloudApiRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
@Slf4j
public class CloudApiRequestUtils {
    private static final Logger LOG = LoggerFactory.getLogger(CloudApiRequestUtils.class);
    /**
     * 线程cloud api request信息维护
     */
    private static final ThreadLocal<CloudApiRequest> REQUEST_BASE_INFO_THREAD_LOCAL =
            new ThreadLocal<>();

    public static CloudApiRequest getRequestInfoFromReq(String requestBodyPayload) {
        CloudApiRequest cloudApiRequest = new CloudApiRequest();
        try {
            log.info("requestBody:{}",requestBodyPayload);
            JsonNode requestBody = JsonUtil.getJsonNode(requestBodyPayload);

            // 解析requestBody，转为cloudapi对象
            cloudApiRequest.setRequestId(
                    requestBody.get("RequestId") != null
                            ? requestBody.get("RequestId").asText()
                            : "");

        } catch (Exception e) {
            LOG.error(
                    "[CloudApiUtils] Parse RequestBodyInfo Error, Error Message -> {}",
                    e.getMessage(),
                    e);
        }
        return cloudApiRequest;
    }

    public static CloudApiRequest buildRequestInfo(String requestId) {
        CloudApiRequest cloudApiRequest = new CloudApiRequest();
        cloudApiRequest.setRequestId(requestId);
        return cloudApiRequest;
    }

    /**
     * 获取cloud api request信息
     */
    public static CloudApiRequest getCloudApiRequest() {
        CloudApiRequest cloudApiRequest = REQUEST_BASE_INFO_THREAD_LOCAL.get();
        return cloudApiRequest;
    }

    /**
     * 将cloud api request信息存入threadLocal中
     */
    public static void setCloudApiRequest(CloudApiRequest cloudApiRequest) {
        REQUEST_BASE_INFO_THREAD_LOCAL.set(cloudApiRequest);
    }

    /**
     * 从threadLocal中删除cloud api request信息
     */
    public static void removeCloudApiRequest() {
        REQUEST_BASE_INFO_THREAD_LOCAL.remove();
    }

    /**
     * 生成随机的requestId ，使用uuid算法
     */
    public static String generateRequestId() {
        String requestId = UUID.randomUUID().toString();
        return requestId;
    }
}
