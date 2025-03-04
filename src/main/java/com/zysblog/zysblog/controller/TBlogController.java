package com.zysblog.zysblog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zysblog.zysblog.common.api.ResponseWrapper;
import com.zysblog.zysblog.common.constants.BaseRedisPrefix;
import com.zysblog.zysblog.common.constants.EPublish;
import com.zysblog.zysblog.common.holder.LocalRequestHolder;
import com.zysblog.zysblog.common.util.CloudApi3Util;
import com.zysblog.zysblog.common.util.IpUtils;
import com.zysblog.zysblog.common.util.RedisUtil;
import com.zysblog.zysblog.dto.request.BlogByTagRequest;
import com.zysblog.zysblog.entity.TBlog;
import com.zysblog.zysblog.service.RedisOperateService;
import com.zysblog.zysblog.service.TBlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 博客表 前端控制器
 * </p>
 *
 * @author zy
 * @since 2024-10-30
 */
@RestController
@RequestMapping("/t-blog")
@Api(value = "文章相关接口", tags = {"文章相关接口"})
public class TBlogController {
    @Autowired
    private TBlogService blogService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedisOperateService redisOperateService;

    @ApiOperation(value = "通过Uid获取博客内容", notes = "通过Uid获取博客内容")
    @GetMapping("/getBlogByUid")
    public ResponseWrapper<TBlog> getBlogByUid(@ApiParam(name = "uid", value = "博客UID", required = false) @RequestParam(name = "uid", required = false) String uid) {

        HttpServletRequest request = LocalRequestHolder.getRequest();
        String ip = IpUtils.getIpAddr(request);
        if (StringUtils.isEmpty(uid)) {
            return CloudApi3Util.getApi3Response(null);
        }
        TBlog blog = null;
        if (StringUtils.isNotEmpty(uid)) {
            blog = blogService.getById(uid);
        }
        if (blog == null || !blog.getStatus() || EPublish.NO_PUBLISH.equals(blog.getIsPublish())) {
            return CloudApi3Util.getApi3Response(null);
        }


        //设置博客标签
        blogService.setTagByBlog(blog);

        //获取分类
        blogService.setSortByBlog(blog);

        //设置博客标题图
        //setPhotoListByBlog(blog);

        //从Redis取出数据，判断该用户是否点击过
        String jsonResult = redisUtil.get("BLOG_CLICK:" + ip + "#" + blog.getUid());

        if (StringUtils.isEmpty(jsonResult)) {

            //给博客点击数增加
            Integer clickCount = blog.getClickCount() + 1;
            blog.setClickCount(clickCount);
            blog.updateById();

            //将该用户点击记录存储到redis中, 24小时后过期
            redisUtil.setEx(BaseRedisPrefix.BLOG_CLICK + ":" + ip + "#" + blog.getUid(), blog.getClickCount().toString(),
                    24, TimeUnit.HOURS);
        }
        return CloudApi3Util.getApi3Response(blog);

    }
    @ApiOperation(value = "通过Uid获取博客点赞数", notes = "通过Uid获取博客点赞数")
    @GetMapping("/getBlogPraiseCountByUid")
    public ResponseWrapper<Integer> getBlogPraiseCountByUid(@ApiParam(name = "uid", value = "博客UID", required = false) @RequestParam(name = "uid", required = false) String uid) {

        return CloudApi3Util.getApi3Response(redisOperateService.getBlogPraiseCountByUid(uid));
    }

    //@BussinessLog(value = "通过Uid给博客点赞", behavior = EBehavior.BLOG_PRAISE)
    @ApiOperation(value = "通过Uid给博客点赞", notes = "通过Uid给博客点赞")
    @GetMapping("/praiseBlogByUid")
    public ResponseWrapper<Integer> praiseBlogByUid(@ApiParam(name = "uid", value = "博客UID", required = false) @RequestParam(name = "uid", required = false) String uid
    ,HttpServletRequest request) {
        if (StringUtils.isEmpty(uid)) {
            boolean res = false;
            return CloudApi3Util.getApi3Response(0);
        }
        Integer count =  blogService.praiseBlogByUid(uid,request);
        return CloudApi3Util.getApi3Response(count);
    }

    @ApiOperation(value = "根据标签Uid获取相关的博客", notes = "根据标签获取相关的博客")
    @GetMapping("/getSameBlogByTagUid")
    public ResponseWrapper<IPage<TBlog>> getSameBlogByTag(@RequestBody BlogByTagRequest request) {
        return CloudApi3Util.getApi3Response(blogService.getSameBlogByTagUid(request));
    }

}
