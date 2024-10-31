package com.zysblog.zysblog.common.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api3PageResponseWrapper<T> {

    @JsonProperty("Response")
    private CallResponse<T> response;

    public static <T> Api3PageResponseWrapper<T> success(
            T data, Long totalCount, String requestId) {
        return new Api3PageResponseWrapper<>(new CallResponse<>(data, totalCount, requestId));
    }

    public static <T> Api3PageResponseWrapper<T> error(
            String code, String message, String requestId) {
        return new Api3PageResponseWrapper<>(new CallResponse<>(code, message, requestId));
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

        @JsonProperty("TotalCount")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Long totalCount;

        @JsonProperty("Error")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private ErrorResponse error;

        public CallResponse(T data, Long totalCount, String requestId) {
            super();
            this.data = data;
            this.totalCount = totalCount;
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
}
