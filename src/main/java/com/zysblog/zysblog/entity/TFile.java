package com.zysblog.zysblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文件表
 * </p>
 *
 * @author zy
 * @since 2024-12-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TFile extends Model<TFile> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一uid
     */
    @TableId(value = "uid", type = IdType.UUID)
    private String uid;

    /**
     * 旧文件名
     */
    private String fileOldName;

    /**
     * 文件名
     */
    private String picName;

    /**
     * 文件地址
     */
    private String picUrl;

    /**
     * 文件扩展名
     */
    private String picExpandedName;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件分类uid
     */
    private String fileSortUid;

    /**
     * 管理员uid
     */
    private String adminUid;

    /**
     * 用户uid
     */
    private String userUid;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * oss地址
     */
    private String ossNiuUrl;


    @Override
    protected Serializable pkVal() {
        return this.uid;
    }

}
