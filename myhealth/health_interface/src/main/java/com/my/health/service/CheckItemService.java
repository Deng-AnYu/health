package com.my.health.service;

import com.my.health.pojo.PageResult;
import com.my.health.pojo.QueryPageBean;
import com.my.health.pojo.CheckItem;

import java.util.List;

/**
 * @Author: Deng
 * @date: 2020-04-02 15:56
 * @description:
 */
public interface CheckItemService {

    List<CheckItem> findAll();

    void add(CheckItem checkItem);

    PageResult findPage(QueryPageBean queryPageBean);

    void delete(Integer id);

    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);
}

