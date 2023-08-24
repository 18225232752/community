package com.zxh.community.mapper;

import com.zxh.community.CommunityApplication;
import com.zxh.community.entity.LoginTicket;
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
 * @date 2023/8/23 14:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class LoginTicketMapperTest {

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    public void testInsertLoginTicket() {
        LoginTicket loginTicket = new LoginTicket(0,
                101,
                "test",
                0,
                new Date(System.currentTimeMillis() + 1000 * 60 * 10));
        System.out.println("loginTicket = " + loginTicket);
        int i = loginTicketMapper.insertLoginTicket(loginTicket);
        System.out.println("i = " + i);
        System.out.println("loginTicket = " + loginTicket);
    }

    @Test
    public void testSelectByTicket() {
        LoginTicket ticket = loginTicketMapper.selectByTicket("test");
        System.out.println("ticket = " + ticket);
    }

    @Test
    public void testUpdateStatus() {
        int i = loginTicketMapper.updateStatus("test", 1);
        System.out.println("i = " + i);
    }
}