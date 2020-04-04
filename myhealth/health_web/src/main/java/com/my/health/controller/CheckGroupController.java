package com.my.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.my.health.MessageConstant;
import com.my.health.Result;
import com.my.health.pojo.CheckGroup;
import com.my.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try {
            checkGroupService.add(checkGroup,checkitemIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }

    }

}
