package com.zxh.community.service;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/28 19:29
 */
public interface LikeService {

    // 点赞
    void like(int userId, int entityType, int entityId, int entityUserId);

    // 查询实体的点赞数
    long findEntityLikeCount(int entityType, int entityId);

    // 查询某人对某实体的点赞状态
    int findEntityLikeStatus(int userId, int entityType, int entityId);

    // 查询某个用户获得的赞
    int findUserLikeCount(int userId);
}
