package com.zysblog.zysblog.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode implements CloudApiErrorCode {
    ILLEGAL_PARAMETERS("IllegalParameters", "参数异常"),
    MAIL_REPEAT_EXCEPTION("MailRepeatExceptions", "当前邮箱已经发送验证码"),

    CALL_API_ERROR("CallApiError", "调用其他模块接口失败"),;


    /** 错误码 */
    private String code;

    /** 错误信息描述 */
    private String message;
}
