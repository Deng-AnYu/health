package com.my.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.my.health.constant.MessageConstant;
import com.my.health.pojo.MyOrderSetting;
import com.my.health.pojo.OrderSetting;
import com.my.health.pojo.Result;
import com.my.health.service.OrderSettingService;
import com.my.health.util.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Deng
 * @date: 2020-04-06 20:16
 * @description:
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    //上传文件
    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile) {
        try {
            System.out.println("************" + excelFile.getOriginalFilename());
            List<String[]> excel = POIUtils.readExcel(excelFile);
            if (excel != null) {
                ArrayList<OrderSetting> list = new ArrayList<>();
                for (String[] row : excel) {
                    OrderSetting orderSetting = new OrderSetting(new Date(row[0]), Integer.parseInt(row[1]));
                    orderSetting.setReservations(0);//已经预约人数 默认0
                    list.add(orderSetting);
                }
//                for (String[] str : excel) {
//                    OrderSetting orderSetting = new OrderSetting();
//                    orderSetting.setOrderDate(new Date(str[0]));//预约日期
//                    orderSetting.setNumber(Integer.parseInt(str[1]));//可预约人数
//                    orderSetting.setReservations(0);//已经预约人数 默认0
//                    list.add(orderSetting);
//                }

                orderSettingService.add(list);
                return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
            }
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    //根据年月来获得时间数据
    //需要返回一个list集合,里面装着MyOrderSetting对象
    @RequestMapping("/getDataByYearAndMonth")
    public Result getDataByYearAndMonth(String date) {
        try {
            List<MyOrderSetting> list = orderSettingService.getDataByYearAndMonth(date);
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    //单个设置预约数据
    @RequestMapping("/editOrderByBtn")
    public Result setOrderByBtn(@RequestBody OrderSetting orderSetting){
        try {
            orderSettingService.editOrderByBtn(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }

    }

}