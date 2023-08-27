package com.zxh.community.util;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/22 19:59
 */
public interface CommunityConstant {

    // 激活成功
    int ACTIVATION_SUCCESS = 0;

    // 重复激活
    int ACTIVATION_REPEAT = 1;

    // 重复激活
    int ACTIVATION_FAILURE = 2;

    // 登录凭证默认有效时长(12小时)
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    // 登录凭证"记住我"状态有效时长
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;

    // 实体类型：帖子
    int ENTITY_TYPE_POST = 1;

    // 实体类型：评论
    int ENTITY_TYPE_COMMENT = 2;
}
