package com.zxh.community.util;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/28 18:48
 */
public class RedisKeyUtil {

    private static final String SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKE = "like:entity";

    // 某个实体(帖子、评论)的点赞量
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }
}
