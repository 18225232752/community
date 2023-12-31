package com.zxh.community.controller;

import com.mchange.v1.identicator.IdList;
import com.zxh.community.entity.Comment;
import com.zxh.community.entity.DiscussPost;
import com.zxh.community.entity.Event;
import com.zxh.community.event.EventProducer;
import com.zxh.community.service.CommentService;
import com.zxh.community.service.DiscussPostService;
import com.zxh.community.util.CommunityConstant;
import com.zxh.community.util.HostHolder;
import com.zxh.community.util.RedisKeyUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/27 13:54
 */
@Controller
@RequestMapping("/comment")
public class CommentController implements CommunityConstant {

    @Resource(name = "commentServiceImpl")
    private CommentService commentService;

    @Resource(name = "eventProducer")
    private EventProducer eventProducer;

    @Resource(name = "hostHolder")
    private HostHolder hostHolder;

    @Resource(name = "discussPostServiceImpl")
    private DiscussPostService discussPostService;

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    @PostMapping("/add/{discussPostId}")
    public String addComment(@PathVariable int discussPostId, Comment comment) {
        comment.setUserId(hostHolder.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentService.addComment(comment);

        // 触发评论事件
        Event event = new Event()
                .setTopic(TOPIC_COMMENT)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(comment.getEntityType())
                .setEntityId(comment.getEntityId())
                .setData("postId", discussPostId);
        if (comment.getEntityType() == ENTITY_TYPE_POST) {
            System.out.println("评论帖子");
            DiscussPost target = discussPostService.findDiscussPostById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        } else if (comment.getEntityType() == ENTITY_TYPE_COMMENT) {
            System.out.println("评论回复");
            Comment target = commentService.findCommentById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }
        eventProducer.fireEvent(event);

        // 帖子评论数发生变化，更新es中的帖子数据
        if (comment.getEntityType() == ENTITY_TYPE_POST) {
            // 触发发帖事件
            event = new Event()
                    .setTopic(TOPIC_PUBLISH)
                    .setUserId(comment.getUserId())
                    .setEntityType(ENTITY_TYPE_POST)
                    .setEntityId(discussPostId);
            eventProducer.fireEvent(event);

            // 帖子id写入redis以重新计算分数
            String postScoreKey = RedisKeyUtil.getPostScoreKey();
            redisTemplate.opsForSet().add(postScoreKey, discussPostId);
        }

        return "redirect:/discuss/detail/" + discussPostId;
    }
}
