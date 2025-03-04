package com.zysblog.zysblog.common.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWrapper<T> {
    @JsonProperty("Response")
    private CallResponse<T> response;

    public static <T> ResponseWrapper<T> success(T data, String requestId) {
        return new ResponseWrapper<>(new CallResponse<>(data, requestId));
    }
    public static <T> ResponseWrapper<T> error(String code, String message, String requestId) {
        return new ResponseWrapper<>(new CallResponse<>(code, message, requestId));
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CallResponse<T> {

        @JsonProperty("RequestId")
        private String requestId;

        @JsonProperty("Data")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private T data;

        @JsonProperty("ProductList")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private T productList;

        @JsonProperty("Error")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private ErrorResponse error;

        public CallResponse(T data, String requestId) {
            super();
            this.data = data;
            this.requestId = requestId;
        }

        /**
         * 构建License server采集的返回对象，要按照License server要求的数据格式进行返回。这个属于定制化方法，非License不要使用
         */
        public CallResponse(T data, String requestId, String type) {
            super();
            this.productList = data;
            this.requestId = requestId;
        }

        public CallResponse(String code, String message, String requestId) {
            super();
            this.error = new ErrorResponse(code, message);
            this.requestId = requestId;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorResponse {

        @JsonProperty("Code")
        private String code;

        @JsonProperty("Message")
        private String message;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageResponse<T> {

        @JsonProperty("Data")
        private List<T> data;

        @JsonProperty("TotalCount")
        private Long totalCount;
    }
}
