package com.my.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.my.health.constant.MessageConstant;
import com.my.health.pojo.PageResult;
import com.my.health.pojo.QueryPageBean;
import com.my.health.pojo.Result;
import com.my.health.pojo.CheckItem;
import com.my.health.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Deng
 * @date: 2020-03-29 22:52
 * @description:
 */

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    //获得所有检查项
    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<CheckItem> list = checkItemService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    //新增检查项
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    //分页查询
    //return:PageResult
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            String queryString = queryPageBean.getQueryString();
            if (queryString != null && !queryString.equals("")) {
                queryString = "%" + queryString + "%";
                queryPageBean.setQueryString(queryString);
            }
            return checkItemService.findPage(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //删除检查项
    //return:PageResult
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        //先检查,这个检查项有没有和检查组关联,有就不能删
        //没有就删
        try {
            checkItemService.delete(id);
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (e.getMessage().equals(MessageConstant.ERROR_CHECKITEM_CHECKGROUP)) {
                //todo  没准这里有问题
                return new Result(false, MessageConstant.ERROR_CHECKITEM_CHECKGROUP);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(false, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    //根据id获取检查项
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            CheckItem checkItem = checkItemService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.edit(checkItem);
            return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

}
