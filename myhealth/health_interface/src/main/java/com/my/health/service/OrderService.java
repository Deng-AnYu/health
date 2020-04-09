package com.my.health.service;

import com.my.health.pojo.Result;

import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-04-09 16:40
 * @description:
 */
public interface OrderService {
    Result order(Map map) throws Exception;

    Map findById(Integer id);
}
