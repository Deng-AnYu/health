package com.my.health.controller;

import com.my.health.constant.MessageConstant;
import com.my.health.pojo.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Deng
 * @date: 2020-04-12 17:39
 * @description:
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @RequestMapping("/getName")
    public Result getName(){

        try {
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User)
                            SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            String name = SecurityContextHolder.getContext().getAuthentication().getName();
//            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,name);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_USERNAME_FAIL);
        }

    }

}
