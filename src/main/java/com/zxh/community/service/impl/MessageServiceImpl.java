package com.zxh.community.service.impl;

import com.zxh.community.entity.Message;
import com.zxh.community.mapper.MessageMapper;
import com.zxh.community.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/27 20:56
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Resource(name = "messageMapper")
    private MessageMapper messageMapper;

    @Override
    public List<Message> findConversations(int userId, int offset, int limit) {
        return messageMapper.selectConversations(userId, offset, limit);
    }

    @Override
    public int findConversationCount(int userId) {
        return messageMapper.selectConversationCount(userId);
    }

    @Override
    public List<Message> findLetters(String conversationId, int offset, int limit) {
        return messageMapper.selectLetters(conversationId, offset, limit);
    }

    @Override
    public int findLetterCount(String conversationId) {
        return messageMapper.selectLetterCount(conversationId);
    }

    @Override
    public int findLetterUnreadCount(int userId, String conversationId) {
        return messageMapper.selectLetterUnreadCount(userId, conversationId);
    }
}
