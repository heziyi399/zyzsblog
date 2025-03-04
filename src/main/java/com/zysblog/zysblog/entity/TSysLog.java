package com.zysblog.zysblog.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zy
 * @since 2024-12-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TSysLog extends Model<TSysLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一uid
     */
    private String uid;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 管理员uid
     */
    private String adminUid;

    /**
     * 请求ip地址
     */
    private String ip;

    /**
     * 请求url
     */
    private String url;

    /**
     * 请求方式
     */
    private String type;

    /**
     * 请求类路径
     */
    private String classPath;

    /**
     * 请求方法名
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 描述
     */
    private String operation;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * ip来源
     */
    private String ipSource;

    /**
     * 方法请求花费的时间
     */
    private Integer spendTime;


    @Override
    protected Serializable pkVal() {
        return this.uid;
    }

}
