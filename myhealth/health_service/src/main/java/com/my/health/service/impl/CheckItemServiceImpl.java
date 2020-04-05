package com.my.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.my.health.constant.MessageConstant;
import com.my.health.pojo.PageResult;
import com.my.health.pojo.QueryPageBean;
import com.my.health.dao.CheckItemDao;
import com.my.health.pojo.CheckItem;
import com.my.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Deng
 * @date: 2020-04-02 16:03
 * @description:
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    //  查询所有检查项
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    //添加检查项
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }


    //分页查询
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());//当前页码  每页显示多少条记录
        List<CheckItem> checkItemList =
                checkItemDao.selectByCondition(queryPageBean.getQueryString());
        //封装分页PageInfo对象
        PageInfo<CheckItem> pageInfo = new PageInfo<>(checkItemList);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());

    }

    //删除检查项
    @Override
    public void delete(Integer itemId) {
        //先判断是否和检查组有关联,有就不删
        int isLinked = checkItemDao.checkItemWithGroup(itemId);
        if (isLinked > 0) {
            throw new RuntimeException(MessageConstant.ERROR_CHECKITEM_CHECKGROUP);
        } else {
            checkItemDao.delete(itemId);
        }
    }

    @Override
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }


}
