package com.zxh.community.mapper;

import com.zxh.community.CommunityApplication;
import com.zxh.community.entity.Page;
import com.zxh.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/21 16:34
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    private User user = null;

    private int i;

    @Test
    public void testSelectById() {
        user = userMapper.selectById(101);
        System.out.println("user = " + user);
    }

    @Test
    public void testSelectByName() {
        user = userMapper.selectByName("liubei");
        System.out.println("user = " + user);
    }

    @Test
    public void testSelectByEmail() {
        user = userMapper.selectByEmail("nowcoder101@sina.com");
        System.out.println("user = " + user);
    }

    @Test
    public void testInsertUser() {
        user = new User();
        user.setUsername("test");
        user.setPassword("123456");
        user.setSalt("abd");
        user.setEmail("test@qq.com");
        user.setHeaderUrl("http://www.nowcoder.com/101.png");
        user.setCreateTime(new Date());

        i = userMapper.insertUser(user);
        System.out.println("i = " + i);
        System.out.println("user.getId() = " + user.getId());
    }

    @Test
    public void testUpdateStatus() {
        i = userMapper.updateStatus(151, 1);
        System.out.println("i = " + i);
    }

    @Test
    public void testUpdateHeader() {
        i = userMapper.updateHeader(151, "http://www.nowcoder.com/151.png");
        System.out.println("i = " + i);
    }

    @Test
    public void testUpdatePassword() {
        i = userMapper.updatePassword(151, "hello");
        System.out.println("i = " + i);
    }

}