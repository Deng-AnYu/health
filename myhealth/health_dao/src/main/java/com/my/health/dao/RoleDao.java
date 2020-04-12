package com.my.health.dao;

import com.my.health.pojo.Role;

import java.util.Set;

/**
 * @Author: Deng
 * @date: 2020-04-12 16:30
 * @description:
 */
public interface RoleDao {
    Set<Role> getRolesByUserId(Integer userId);
}
