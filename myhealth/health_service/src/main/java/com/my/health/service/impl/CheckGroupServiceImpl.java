package com.my.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.my.health.dao.CheckGroupDao;
import com.my.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-04-03 21:01
 * @description:
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService{

    @Autowired
    private CheckGroupDao checkGroupDao;

}
