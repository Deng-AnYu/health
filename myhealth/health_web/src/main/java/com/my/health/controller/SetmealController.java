package com.my.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.my.health.constant.MessageConstant;
import com.my.health.constant.RedisConstant;
import com.my.health.pojo.PageResult;
import com.my.health.pojo.QueryPageBean;
import com.my.health.pojo.Result;
import com.my.health.pojo.Setmeal;
import com.my.health.service.SetmealService;
import com.my.health.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

/**
 * @Author: Deng
 * @date: 2020-04-04 18:26
 * @description:
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

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

    //添加套餐
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        try {
            setmealService.add(setmeal, checkgroupIds);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            String queryString = queryPageBean.getQueryString();
            if (queryString != null && !queryString.equals("")) {
                queryString = "%" + queryString + "%";
                queryPageBean.setQueryString(queryString);
            }
            return setmealService.findPage(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //删除套餐
    @RequestMapping("/deleteById")
    public Result deleteById(Integer id, Integer[] checkgroupIds) {
        try {
            setmealService.deleteById(id, checkgroupIds);
            return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_SETMEAL_FAIL);
        }
    }

}
