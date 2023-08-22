package com.zxh.community.service.impl;

import com.zxh.community.entity.DiscussPost;
import com.zxh.community.mapper.DiscussPostMapper;
import com.zxh.community.service.DiscussPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/21 18:26
 */
@Service
public class DiscussPostServiceImpl implements DiscussPostService {

    @Resource(name = "discussPostMapper")
    private DiscussPostMapper discussPostMapper;

    @Override
    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    @Override
    public int findDiscussPostRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}
