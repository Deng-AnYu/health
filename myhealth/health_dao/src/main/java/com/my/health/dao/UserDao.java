package com.my.health.dao;

import com.my.health.pojo.User;

/**
 * @Author: Deng
 * @date: 2020-04-12 9:51
 * @description:
 */
public interface UserDao {
    User findUserByUsername(String s);
}
