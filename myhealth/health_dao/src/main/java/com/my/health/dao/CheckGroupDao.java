package com.my.health.dao;

import com.my.health.pojo.CheckGroup;

import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-04-03 21:03
 * @description:
 */
public interface CheckGroupDao {

    Map findAllItem();

    void add(CheckGroup checkGroup);

    void addLinked(Map<String, Integer> map);

    int selectByCondition(String code);
}
