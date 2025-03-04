package com.zysblog.zysblog.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 博客分类表
 * </p>
 *
 * @author zy
 * @since 2024-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TBlogSort extends Model<TBlogSort> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一uid
     */
    private String uid;

    /**
     * 分类内容
     */
    private String sortName;

    /**
     * 分类简介
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 排序字段，越大越靠前
     */
    private Integer sort;

    /**
     * 点击数
     */
    private Integer clickCount;


    @Override
    protected Serializable pkVal() {
        return this.uid;
    }

}
