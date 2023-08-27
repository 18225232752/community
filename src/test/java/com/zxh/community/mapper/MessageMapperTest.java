package com.zxh.community.mapper;

import com.zxh.community.CommunityApplication;
import com.zxh.community.entity.Message;
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
 * @date 2023/8/27 17:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MessageMapperTest {

    @Autowired
    private MessageMapper messageMapper;

    @Test
    public void testSelectConversations() {
        List<Message> messages = messageMapper.selectConversations(111, 0, 20);
        for (Message message : messages) {
            System.out.println(message);
        }
    }

    @Test
    public void testSelectConversationCount() {
        int rows = messageMapper.selectConversationCount(111);
        System.out.println(rows);
    }

    @Test
    public void testSelectLetters() {
        List<Message> messages = messageMapper.selectLetters("111_112", 0, 10);
        for (Message message : messages) {
            System.out.println(message);
        }
    }

    @Test
    public void testSelectLetterCount() {
        int rows = messageMapper.selectLetterCount("111_112");
        System.out.println(rows);
    }

    @Test
    public void testSelectLetterUnreadCount() {
        int rows = messageMapper.selectLetterUnreadCount(131, "111_131");
        System.out.println(rows);
    }
}