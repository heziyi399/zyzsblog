package com.zysblog.zysblog.common.util;

import com.zysblog.zysblog.common.api.Api3PageResponseWrapper;
import com.zysblog.zysblog.common.api.CloudApiRequest;
import com.zysblog.zysblog.common.api.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CloudApi3Util {


    /**
     * 构建api3返回请求对象
     *
     * @param data 返回数据
     * @return Api3ResponseWrapper
     */
    public static <T> ResponseWrapper<T> getApi3Response(T data) {
        CloudApiRequest cloudApiRequest = CloudApiRequestUtils.getCloudApiRequest();
        return ResponseWrapper.success(data, cloudApiRequest.getRequestId());
    }

    public static <T> Api3PageResponseWrapper<T> getApi3PageResponse(T data, Long totalCount) {
        CloudApiRequest cloudApiRequest = CloudApiRequestUtils.getCloudApiRequest();
        return Api3PageResponseWrapper.success(data, totalCount, cloudApiRequest.getRequestId());
    }


    /**
     * 条件判断
     */
    public enum WhereType {
        LIKE,
        EQUAL;
    }


}
