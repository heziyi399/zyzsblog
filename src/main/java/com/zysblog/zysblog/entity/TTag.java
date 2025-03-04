package com.zysblog.zysblog.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 标签表
 * </p>
 *
 * @author zy
 * @since 2024-12-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TTag extends Model<TTag> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一uid
     */
    private String uid;

    /**
     * 标签内容
     */
    private String content;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 标签简介
     */
    private Integer clickCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 排序字段，越大越靠前
     */
    private Integer sort;


    @Override
    protected Serializable pkVal() {
        return this.uid;
    }

}
