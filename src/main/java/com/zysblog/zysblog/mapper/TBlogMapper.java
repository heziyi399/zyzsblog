package com.zysblog.zysblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zysblog.zysblog.entity.TBlog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 博客表 Mapper 接口
 * </p>
 *
 * @author zy
 * @since 2024-10-30
 */
@Mapper
public interface TBlogMapper extends BaseMapper<TBlog> {

}
