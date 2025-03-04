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
    USER_STATUS_ERROR("UserStatusError", "用户状态异常"),

    CALL_API_ERROR("CallApiError", "调用其他模块接口失败"),
    INTERNAL_ERROR("InternalError", "系统内部异常"),
    INVALID_TOKEN("InvalidToken","token无效"),
    REPEAT_SUBMIT("RepeatSubmit","重复提交"),
    ;


    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息描述
     */
    private String message;
}
