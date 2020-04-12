package com.my.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.my.health.dao.UserDao;
import com.my.health.pojo.User;
import com.my.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Deng
 * @date: 2020-04-12 9:50
 * @description:
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findUserByUsername(String s) {
        return userDao.findUserByUsername(s);
    }
}
