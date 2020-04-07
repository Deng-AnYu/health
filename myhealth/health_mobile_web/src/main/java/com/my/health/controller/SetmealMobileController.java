package com.my.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.my.health.constant.MessageConstant;
import com.my.health.constant.RedisConstant;
import com.my.health.pojo.Result;
import com.my.health.pojo.Setmeal;
import com.my.health.service.SetmealService;
import com.my.health.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @Author: Deng
 * @date: 2020-04-07 20:03
 * @description:
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    //上传文件到七牛云,作为预览用,并返回文件名
    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile) throws IOException {
        try {
            String filename = imgFile.getOriginalFilename();
            int lastIndexOf = filename.lastIndexOf(".");
            //获得文件名后缀
            String suffix = filename.substring(lastIndexOf);
            filename = UUID.randomUUID().toString() + suffix;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), filename);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, filename);
            return new Result(true, MessageConstant.UPLOAD_SUCCESS, filename);

        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }



    //获取所有套餐信息
    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        try {
            List<Setmeal> list=setmealService.getAll();
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }


    //根据id获取套餐信息
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
           Setmeal setmeal= setmealService.getById(id);
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }



}
