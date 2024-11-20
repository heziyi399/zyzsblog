package com.zysblog.zysblog.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AppConfig extends Model<AppConfig> {
    @TableId(value = "uid", type = IdType.AUTO)
    private Long id;


    private String configKey;


    private String configValue;

    // Getters and Setters
}
