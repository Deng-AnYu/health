package com.my.health.dao;

import com.my.health.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-04-03 21:03
 * @description:
 */
public interface CheckGroupDao {

    void add(CheckGroup checkGroup);

    void addLinked(Map<String, Integer> map);

    List<CheckGroup> selectByCondition(String code);

    Integer[] findGroupLinkedItem(Integer id);

    CheckGroup findGroupById(Integer id);

    void editCheckGroup(CheckGroup checkGroup);

    void deleteLinked(Integer id);

    void deleteGroupById(Integer id);

}
