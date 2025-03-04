package com.zysblog.zysblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;


import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zy
 * @since 2024-12-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ExceptionLog extends Model<ExceptionLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String exceptionMessage;

    private String stackTrace;

    /**
     * 请求参数
     */
    private String expRequParam;

    /**
     * 用户ID
     */
    private String operUserId;

    /**
     * 请求URI
     */
    private String operUri;

    /**
     * 操作时间
     */
    private Date operCreateTime;
    /**
     * 调用方法
     */
    private String method;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
