package com.zxh.community.controller;

import com.zxh.community.entity.Comment;
import com.zxh.community.entity.DiscussPost;
import com.zxh.community.entity.Event;
import com.zxh.community.event.EventProducer;
import com.zxh.community.service.CommentService;
import com.zxh.community.service.DiscussPostService;
import com.zxh.community.util.CommunityConstant;
import com.zxh.community.util.HostHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;

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
            DiscussPost target = discussPostService.findDiscussPostById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        } else if (comment.getEntityType() == ENTITY_TYPE_COMMENT) {
            Comment target = commentService.findCommentById(comment.getId());
            event.setEntityUserId(target.getUserId());
        }
        eventProducer.fireEvent(event);

        return "redirect:/discuss/detail/" + discussPostId;
    }
}
