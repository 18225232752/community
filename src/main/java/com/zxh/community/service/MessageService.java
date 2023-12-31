package com.zxh.community.service;

import com.zxh.community.entity.Message;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/27 20:53
 */
public interface MessageService {
    List<Message> findConversations(int userId, int offset, int limit);

    int findConversationCount(int userId);

    List<Message> findLetters(String conversationId, int offset, int limit);

    int findLetterCount(String conversationId);

    int findLetterUnreadCount(int userId, String conversationId);

    int addMessage(Message message);

    int readMessage(List<Integer> ids);

    Message findLatestNotice(int userId, String topic);

    int findNoticeCount(int userId, String topic);

    int findNoticeUnreadCount(int userId, String topic);

    List<Message> findNotices(int userId, String topic, int offset, int limit);
}
