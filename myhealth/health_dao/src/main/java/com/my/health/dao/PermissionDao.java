package com.my.health.dao;

import com.my.health.pojo.Permission;

import java.util.Set;

/**
 * @Author: Deng
 * @date: 2020-04-12 16:46
 * @description:
 */
public interface PermissionDao {
    Set<Permission> getPermissionsByRoleId(Integer roleId);
}
