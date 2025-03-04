package com.zysblog.zysblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zysblog.zysblog.dto.request.BlogByTagRequest;
import com.zysblog.zysblog.entity.TBlog;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 博客表 服务类
 * </p>
 *
 * @author zy
 * @since 2024-10-30
 */
public interface TBlogService extends IService<TBlog> {
    TBlog setTagByBlog(TBlog blog);
    TBlog setSortByBlog(TBlog blog);

    Integer praiseBlogByUid(String uid, HttpServletRequest request);

    IPage<TBlog> getSameBlogByTagUid(BlogByTagRequest request);
}
