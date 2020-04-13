package com.my.health.service;

import com.my.health.pojo.Result;

import java.util.ArrayList;
import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-04-13 17:00
 * @description:
 */
public interface ReportService {
    //根据月份集合,获取对应月份的会员人数
    ArrayList<Integer> getmemberCountByMonth(ArrayList<String> months);

    Result getSetmealReport();

    Map<String, Object> getBusinessReportData() throws Exception;

}
