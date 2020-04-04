package com.my.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.my.health.dao.CheckGroupDao;
import com.my.health.pojo.CheckGroup;
import com.my.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    //添加检查组
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //先用checkGroup创建组
        //再用checkitemIds去中间关联表建立关系
        checkGroupDao.add(checkGroup);
//        int checkgroupId=checkGroupDao.selectByCondition(checkGroup.getCode());
        Integer checkgroupId = checkGroup.getId();
        Map <String,Integer>map=new HashMap<>();
        map.put("checkgroupId",checkgroupId);

        //循环插入到t_checkgroup_checkitem中
        for (Integer checkitemId : checkitemIds) {
            map.put("checkitemId",checkitemId);
            checkGroupDao.addLinked(map);
        }
    }
}
