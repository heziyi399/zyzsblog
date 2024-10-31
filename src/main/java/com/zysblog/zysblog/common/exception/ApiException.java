package com.zysblog.zysblog.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/** @author talentzhang */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** 错误码 */
    private final String code;

    /** 错误信息 */
    private final String message;

    public ApiException(CloudApiErrorCode errorCode) {
        super(errorCode.getCode());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ApiException(CloudApiErrorCode errorCode, String errorMsg) {
        super(errorCode.getCode());
        this.code = errorCode.getCode();
        this.message = errorMsg;
    }
}
