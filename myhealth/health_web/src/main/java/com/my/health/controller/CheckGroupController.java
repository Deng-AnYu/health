package com.my.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.my.health.MessageConstant;
import com.my.health.PageResult;
import com.my.health.QueryPageBean;
import com.my.health.Result;
import com.my.health.pojo.CheckGroup;
import com.my.health.pojo.CheckItem;
import com.my.health.service.CheckGroupService;
import com.my.health.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-04-03 21:00
 * @description:
 */
@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;


    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            checkGroupService.add(checkGroup, checkitemIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
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
            return checkGroupService.findPage(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //查询检查组和关联的检查项的id
    @RequestMapping("/findGroupAndLinkedItem")
    public Result findGroupAndLinkedItem(Integer id) {
        try {
            Map map = checkGroupService.findGroupAndLinkedItem(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
    //编辑检查组
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds ) {
        try {
            checkGroupService.edit(checkGroup,checkitemIds);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    //删除检查组
    @RequestMapping("/deleteById")
    public Result deleteById(Integer id) {
        try {
            checkGroupService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

}
