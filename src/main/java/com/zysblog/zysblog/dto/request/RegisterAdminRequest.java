package com.zysblog.zysblog.dto.request;

import com.zysblog.zysblog.common.api.CloudApiRequest;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterAdminRequest extends CloudApiRequest {
    private String userName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 个人头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 出生年月日
     */
    private LocalDate birthday;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 邮箱验证码
     */
    private String validCode;

    /**
     * 自我简介最多150字
     */
    private String summary;
}
