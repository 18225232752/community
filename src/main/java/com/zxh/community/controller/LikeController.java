package com.zxh.community.controller;

import com.zxh.community.entity.Event;
import com.zxh.community.entity.User;
import com.zxh.community.event.EventProducer;
import com.zxh.community.service.LikeService;
import com.zxh.community.util.CommunityConstant;
import com.zxh.community.util.CommunityUtil;
import com.zxh.community.util.HostHolder;
import com.zxh.community.util.RedisKeyUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/28 19:45
 */
@Controller
public class LikeController implements CommunityConstant {

    @Resource(name = "likeServiceImpl")
    private LikeService likeService;

    @Resource(name = "eventProducer")
    private EventProducer eventProducer;

    @Resource(name = "hostHolder")
    private HostHolder hostHolder;

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    @PostMapping("/like")
    @ResponseBody
    public String like(int entityType, int entityId, int entityUserId, int postId) {
        User user = hostHolder.getUser();

        // 点赞
        likeService.like(user.getId(), entityType, entityId, entityUserId);

        // 点赞数量
        long likeCount = likeService.findEntityLikeCount(entityType, entityId);
        // 点赞状态
        int likeStatus = likeService.findEntityLikeStatus(user.getId(), entityType, entityId);

        // 返回的结果
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeCount);
        map.put("likeStatus", likeStatus);

        // 触发点赞事件
        if (likeStatus == 1) {
            Event event = new Event()
                    .setTopic(TOPIC_LIKE)
                    .setUserId(hostHolder.getUser().getId())
                    .setEntityType(entityType)
                    .setEntityId(entityId)
                    .setEntityUserId(entityUserId)
                    .setData("postId", postId);
            eventProducer.fireEvent(event);
        }

        if (entityType == ENTITY_TYPE_POST) {
            // 帖子id写入redis以重新计算分数
            String postScoreKey = RedisKeyUtil.getPostScoreKey();
            redisTemplate.opsForSet().add(postScoreKey, postId);
        }
        return CommunityUtil.getJSONString(0, null, map);
    }
}
