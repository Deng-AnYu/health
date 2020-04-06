package com.my.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.my.health.pojo.CheckGroup;
import com.my.health.pojo.PageResult;
import com.my.health.pojo.QueryPageBean;
import com.my.health.dao.SetmealDao;
import com.my.health.pojo.Setmeal;
import com.my.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-04-04 20:40
 * @description:
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    //添加套餐
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //先添加套餐
        setmealDao.addSetmeal(setmeal);
        //再循环关系表里添加套餐和检查组的关系
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("setmealId", setmeal.getId());
            for (Integer checkgroupId : checkgroupIds) {
                map.put("checkgroupId", checkgroupId);
                setmealDao.addLinked(map);
            }
        }

    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());//当前页码  每页显示多少条记录
        List<Setmeal> setmealList =
                setmealDao.selectByCondition(queryPageBean.getQueryString());
        //封装分页PageInfo对象
        PageInfo<Setmeal> pageInfo = new PageInfo<>(setmealList);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());


    }

    //删除套餐
    @Override
    public void deleteById(Integer id,Integer[] checkgroupIds) {
        //先把关联删了,再把套餐删了
        setmealDao.deleteLinked(id);
        setmealDao.deleteById(id);

    }

    @Override
    public Map findGroupAndLinkedItem(Integer id) {

        //根据id获取关联的检查项
        Integer[] ids = setmealDao.findSetmealLinkedGroup(id);
        //根据id获取这个检查组的信息
        Setmeal setmeal=setmealDao.getSetmealById(id);

        HashMap<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        map.put("setmeal", setmeal);
        return map;

    }

    //编辑套餐
    @Override
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
        //修改表
        setmealDao.editSetmeal(setmeal);
        //先删除原有的所有关系
        setmealDao.deleteLinked(setmeal.getId());
        //在重新建立一遍
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("setmealId", setmeal.getId());
            for (Integer checkgroupId : checkgroupIds) {
                map.put("checkgroupId", checkgroupId);
                setmealDao.addLinked(map);
            }
        }


    }
}
