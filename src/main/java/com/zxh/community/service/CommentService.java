package com.zxh.community.service;

import com.zxh.community.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/27 10:14
 */
public interface CommentService {

    List<Comment> findCommentsByEntity(int entityType, int entityId, int offset, int limit);

    public int findCommentCount(int entityType, int entityId);

    int addComment(Comment comment);

    Comment findCommentById(int id);
}
