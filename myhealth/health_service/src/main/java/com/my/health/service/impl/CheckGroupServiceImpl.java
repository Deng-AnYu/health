package com.my.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.my.health.pojo.PageResult;
import com.my.health.pojo.QueryPageBean;
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
public class CheckGroupServiceImpl implements CheckGroupService {

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
        Map<String, Integer> map = new HashMap<>();
        map.put("checkgroupId", checkgroupId);

        //循环插入到t_checkgroup_checkitem中
        for (Integer checkitemId : checkitemIds) {
            map.put("checkitemId", checkitemId);
            checkGroupDao.addLinked(map);
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());//当前页码  每页显示多少条记录
        List<CheckGroup> checkGroupList =
                checkGroupDao.selectByCondition(queryPageBean.getQueryString());
        //封装分页PageInfo对象
        PageInfo<CheckGroup> pageInfo = new PageInfo<>(checkGroupList);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());


    }

//    @Override
//    public Integer[] findGroupLinkedItem(Integer id) {
//        return checkGroupDao.findGroupLinkedItem(id);
//    }

    @Override
    public Map findGroupAndLinkedItem(Integer id) {

        //根据id获取关联的检查项
        Integer[] ids = checkGroupDao.findGroupLinkedItem(id);
        //根据id获取这个检查组的信息
        CheckGroup checkGroup = checkGroupDao.findGroupById(id);

        HashMap<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        map.put("checkGroup", checkGroup);
        return map;
    }

    //修改检查组
    //和这个检查组所关联的检查项
    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.editCheckGroup(checkGroup);
        //先删除原有的所有关系
        checkGroupDao.deleteLinked(checkGroup.getId());
        //在重新建立一遍
        Map<String, Integer> map = new HashMap<>();
        map.put("checkgroupId", checkGroup.getId());
        for (Integer checkitemId : checkitemIds) {
            map.put("checkitemId", checkitemId);
            checkGroupDao.addLinked(map);
        }
    }

    @Override
    public void deleteById(Integer id) {
        checkGroupDao.deleteLinked(id);
        checkGroupDao.deleteGroupById(id);
    }

    @Override
    public List<CheckGroup> findAllGroup() {
        return checkGroupDao.findAllGroup();
    }


}
