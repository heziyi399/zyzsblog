package com.zysblog.zysblog.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author zy
 * @since 2024-10-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TAdmin extends Model<TAdmin> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一uid
     */
    @TableId(value = "uid", type = IdType.UUID)
    private String uid;

    /**
     * 用户名
     */
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

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 创建时间，采用插入时自动填充策略
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * QQ号
     */
    private String qqNumber;

    /**
     * 微信号
     */
    private String weChat;

    /**
     * 拥有的角色uid
     */
    private String roleUid;

    /**
     * 介绍
     */
    private String description;


    @Override
    protected Serializable pkVal() {
        return this.uid;
    }

}
