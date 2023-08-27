package com.zxh.community.service.impl;

import com.zxh.community.entity.Comment;
import com.zxh.community.mapper.CommentMapper;
import com.zxh.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/27 10:20
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource(name = "commentMapper")
    private CommentMapper commentMapper;

    @Override
    public List<Comment> findCommentsByEntity(int entityType, int entityId, int offset, int limit) {
        return commentMapper.selectCommentsByEntity(entityType, entityId, offset, limit);
    }

    @Override
    public int findCommentCount(int entityType, int entityId) {
        return commentMapper.selectCountByEntity(entityType, entityId);
    }
}
