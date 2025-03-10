package com.zysblog.zysblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 博客表
 * </p>
 *
 * @author zy
 * @since 2024-10-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TBlog extends Model<TBlog> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一uid
     */
    @TableId(value = "uid", type = IdType.UUID)
    private String uid;

    /**
     * 博客标题
     */
    private String title;

    /**
     * 博客简介
     */
    private String summary;

    /**
     * 博客内容
     */
    private String content;

    /**
     * 标签uid
     */
    private String tagUid;

    /**
     * 博客点击数
     */
    private Integer clickCount;

    /**
     * 博客收藏数
     */
    private Integer collectCount;

    /**
     * 标题图片uid
     */
    private String fileUid;

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
     * 管理员uid
     */
    private String adminUid;

    /**
     * 是否原创（0:不是 1：是）
     */
    private String isOriginal;

    /**
     * 作者
     */
    private String author;

    /**
     * 文章出处
     */
    private String articlesPart;

    /**
     * 博客分类UID
     */
    private String blogSortUid;

    /**
     * 推荐等级(0:正常)
     */
    private Boolean level;

    /**
     * 是否发布：0：否，1：是
     */
    private String isPublish;

    /**
     * 排序字段
     */
    private Integer sort;

    /**
     * 是否开启评论(0:否 1:是)
     */
    private Boolean openComment;

    /**
     * 类型【0 博客， 1：推广】
     */
    private Boolean type;

    /**
     * 外链【如果是推广，那么将跳转到外链】
     */
    private String outsideLink;

    /**
     * 唯一oid
     */
    private Integer oid;

    /**
     * 投稿用户UID
     */
    private String userUid;

    /**
     * 文章来源【0 后台添加，1 用户投稿】
     */
    private Boolean articleSource;


    @Override
    protected Serializable pkVal() {
        return this.uid;
    }
    @TableField(exist = false)
    private List<TTag> tagList;

    /**
     * 标题图
     */
    @TableField(exist = false)
    private List<String> photoList;

    /**
     * 博客分类
     */
    @TableField(exist = false)
    private TBlogSort blogSort;

    /**
     * 博客分类名
     */
    @TableField(exist = false)
    private String blogSortName;

    /**
     * 博客标题图
     */
    @TableField(exist = false)
    private String photoUrl;

    /**
     * 点赞数
     */
    @TableField(exist = false)
    private Integer praiseCount;
}
