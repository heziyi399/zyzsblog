package com.zysblog.zysblog.common.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CloudApiRequest {
    @JsonProperty("RequestId")
    String requestId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "CloudApiRequest{" +
                "requestId='" + requestId + '\'' +
                '}';
    }
}
