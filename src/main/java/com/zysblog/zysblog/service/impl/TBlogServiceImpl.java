package com.zysblog.zysblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zysblog.zysblog.common.constants.BaseRedisPrefix;
import com.zysblog.zysblog.common.constants.EPublish;
import com.zysblog.zysblog.common.util.JwtUtil;
import com.zysblog.zysblog.common.util.RedisUtil;
import com.zysblog.zysblog.dto.request.BlogByTagRequest;
import com.zysblog.zysblog.entity.TBlog;
import com.zysblog.zysblog.entity.TBlogSort;
import com.zysblog.zysblog.entity.TTag;
import com.zysblog.zysblog.mapper.TBlogMapper;
import com.zysblog.zysblog.mapper.TBlogSortMapper;
import com.zysblog.zysblog.mapper.TTagMapper;
import com.zysblog.zysblog.service.TBlogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    @Autowired
    private TTagMapper tagMapper;
    @Autowired
    private TBlogSortMapper blogSortMapper;
    @Autowired
    private RedisUtil redisUtil;
    private final long lockTime = 600000;
    @Override
    public TBlog setTagByBlog(TBlog blog) {
        String tagUid = blog.getTagUid();
        if (!StringUtils.isEmpty(tagUid)) {
            String[] uids = tagUid.split(",");
            List<TTag> tagList = new ArrayList<>();
            for (String uid : uids) {
                TTag tag = tagMapper.selectById(uid);
                if (tag != null && !tag.getStatus()) {
                    tagList.add(tag);
                }
            }
            blog.setTagList(tagList);
        }
        return blog;
    }

    @Override
    public TBlog setSortByBlog(TBlog blog) {
        if (blog != null && !StringUtils.isEmpty(blog.getBlogSortUid())) {
            TBlogSort blogSort = blogSortMapper.selectById(blog.getBlogSortUid());
            blog.setBlogSort(blogSort);
        }
        return blog;
    }

    @Override
    public Integer praiseBlogByUid(String uid, HttpServletRequest request) {
        String praiseResult = redisUtil.get(BaseRedisPrefix.BLOG_PRAISE + BaseRedisPrefix.SEGMENTATION + uid);
        TBlog blog = this.getById(uid);
        String token = request.getHeader("Authorization");
        Map<String, Object> claims = JwtUtil.parseToken(token);
        String userId = (String) claims.get("userId");
        boolean lock = redisUtil.lock(userId+":"+uid,lockTime);
        int count = 0;
        if(lock) {

            if (StringUtils.isEmpty(praiseResult)) {
                //给该博客点赞数
                redisUtil.set(BaseRedisPrefix.BLOG_PRAISE + BaseRedisPrefix.SEGMENTATION + uid, "1");
                count = 1;
                blog.setCollectCount(1);
                blog.updateById();

            } else {
                count = blog.getCollectCount() + 1;
                //给该博客点赞 +1
                redisUtil.set(BaseRedisPrefix.BLOG_PRAISE + BaseRedisPrefix.SEGMENTATION + uid, String.valueOf(count));
                blog.setCollectCount(count);
                blog.updateById();
            }
            return count;
        }else{
            count = blog.getCollectCount();
            return count;
        }
    }

    @Override
    public IPage<TBlog> getSameBlogByTagUid(BlogByTagRequest request) {
        QueryWrapper<TBlog> queryWrapper = new QueryWrapper<>();
        Page<TBlog> page = new Page<>();
        page.setCurrent(request.getCurrentPage());
        page.setSize(request.getPageSize());
        queryWrapper.like("tag_uid", request.getTagUid());
        queryWrapper.orderByDesc("create_time");
        queryWrapper.eq("status", true);
        queryWrapper.eq("is_publish", EPublish.PUBLISH);
        IPage<TBlog> pageList = this.page(page, queryWrapper);
        List<TBlog> list = pageList.getRecords();
        list = this.setTagAndSortByBlogList(list);
        pageList.setRecords(list);
        return pageList;
    }

    List<TBlog> setTagAndSortByBlogList(List<TBlog> list) {
        List<String> sortUids = new ArrayList<>();
        List<String> tagUids = new ArrayList<>();
        list.forEach(item -> {
            if (StringUtils.isNotEmpty(item.getBlogSortUid())) {
                sortUids.add(item.getBlogSortUid());
            }
            if (StringUtils.isNotEmpty(item.getTagUid())) {
                List<String> tagUidList = Arrays.asList(item.getTagUid(), ",");
                for (String itemTagUid : tagUidList) {
                    tagUids.add(itemTagUid);
                }
            }
        });
        Collection<TBlogSort> sortList = new ArrayList<>();
        Collection<TTag> tagList = new ArrayList<>();
        if (!sortUids.isEmpty()) {
            sortList = blogSortMapper.selectBatchIds(sortUids);
        }
        if (!tagUids.isEmpty()) {
            tagList = tagMapper.selectBatchIds(tagUids);
        }
        Map<String, TBlogSort> sortMap = new HashMap<>();
        Map<String, TTag> tagMap = new HashMap<>();
        sortList.forEach(item -> {
            sortMap.put(item.getUid(), item);
        });
        tagList.forEach(item -> {
            tagMap.put(item.getUid(), item);
        });
        for (TBlog item : list) {
            //设置分类
            if (StringUtils.isNotEmpty(item.getBlogSortUid())) {
                item.setBlogSort(sortMap.get(item.getBlogSortUid()));
            }
            //获取标签
            if (StringUtils.isNotEmpty(item.getTagUid())) {
                List<String> tagUidList = Arrays.asList(item.getTagUid(), ",");
                List<TTag> tagListTemp = new ArrayList<TTag>();
                tagUidList.forEach(tag -> {
                    tagListTemp.add(tagMap.get(tag));
                });
                item.setTagList(tagListTemp);
            }
        }

        return list;
    }

}
