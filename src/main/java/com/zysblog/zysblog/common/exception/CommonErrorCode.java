package com.zysblog.zysblog.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CommonErrorCode implements CloudApiErrorCode {

    // 通用错误
    INVALID_PARAMETER_VALUE("InvalidParameterValue", "参数取值错误"),
    INTERNAL_ERROR("FailedOperation", "系统调用异常");

    /** 错误码 */
    private String code;

    /** 错误信息描述 */
    private String message;
}
