package com.zxh.community.mapper;

import com.zxh.community.CommunityApplication;
import com.zxh.community.entity.DiscussPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/21 18:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class DiscussPostMapperTest {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    public void testSelectDiscussPosts() {
        List<DiscussPost> discussPostList = discussPostMapper.selectDiscussPosts(0, 0, 10, 0);
        discussPostList.forEach(System.out::println);
        discussPostList = discussPostMapper.selectDiscussPosts(149, 0, 10, 0);
        discussPostList.forEach(System.out::println);
    }

    @Test
    public void testSelectDiscussPostRows() {
        int rows = discussPostMapper.selectDiscussPostRows(0);
        System.out.println("rows = " + rows);
        rows = discussPostMapper.selectDiscussPostRows(149);
        System.out.println("rows = " + rows);
    }
}