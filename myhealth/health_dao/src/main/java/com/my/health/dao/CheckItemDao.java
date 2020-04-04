package com.my.health.dao;

import com.my.health.PageResult;
import com.my.health.QueryPageBean;
import com.my.health.pojo.CheckGroup;
import com.my.health.pojo.CheckItem;

import java.util.List;

/**
 * @Author: Deng
 * @date: 2020-04-02 16:09
 * @description:
 */
public interface CheckItemDao {

//  查询所有检查项
    List<CheckItem> findAll();

    void add(CheckItem checkItem);

    List<CheckItem> selectByCondition(String queryString);

    void delete(Integer id);

    int checkItemWithGroup(Integer itemId);

    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);


}
