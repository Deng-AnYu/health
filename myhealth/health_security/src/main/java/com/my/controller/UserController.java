package com.my.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Deng
 * @date: 2020-04-11 17:16
 * @description:
 */
@RestController
public class UserController {

    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('add')")
    public String add(){
        System.out.println("进入了add方法");
        return null;
    }

    @RequestMapping("/look")
    @PreAuthorize("hasAnyAuthority()")
//    有任何权限都可以
    public String look(){
        System.out.println("进入了look方法");
        return null;

    }

    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('delete')")
//    有删除权限的才可以
    public String delete(){
        System.out.println("进入了delete方法");
//        return "delete";
        return null;

    }

    @RequestMapping("/getAll")
    @PreAuthorize("hasAnyRole()")
//    有任何角色的都可以
    public String getAll(){
        System.out.println("进入了getAll方法");

        return null;

//        return "getAll";
    }



}
