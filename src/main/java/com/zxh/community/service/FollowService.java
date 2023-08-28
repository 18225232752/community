package com.zxh.community.service;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/28 23:05
 */
public interface FollowService {

    void follow(int userId, int entityType, int entityId);

    void unfollow(int userId, int entityType, int entityId);

    long findFolloweeCount(int userId, int entityType);

    long findFollowerCount(int entityType, int entityId);

    boolean hasFollowed(int userId, int entityType, int entityId);
}
