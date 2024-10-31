package com.zysblog.zysblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zysblog.zysblog.entity.TBlog;
import com.zysblog.zysblog.mapper.TBlogMapper;
import com.zysblog.zysblog.service.TBlogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 博客表 服务实现类
 * </p>
 *
 * @author zy
 * @since 2024-10-30
 */
@Service
public class TBlogServiceImpl extends ServiceImpl<TBlogMapper, TBlog> implements TBlogService {

}
