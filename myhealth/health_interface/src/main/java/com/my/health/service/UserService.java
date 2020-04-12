package com.my.health.service;

import com.my.health.pojo.User;

/**
 * @Author: Deng
 * @date: 2020-04-12 9:49
 * @description:
 */
public interface UserService {
    User findUserByUsername(String s);
}
