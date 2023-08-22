package com.zxh.community.service;

import com.zxh.community.entity.User;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/21 18:31
 */
public interface UserService {
    User findUserById(int id);
}
