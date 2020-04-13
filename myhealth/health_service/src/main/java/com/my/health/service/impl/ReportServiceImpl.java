package com.my.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.my.health.constant.MessageConstant;
import com.my.health.dao.MemberDao;
import com.my.health.dao.OrderDao;
import com.my.health.dao.ReportDao;
import com.my.health.dao.SetmealDao;
import com.my.health.pojo.Result;
import com.my.health.pojo.Setmeal;
import com.my.health.service.ReportService;
import com.my.health.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-04-13 17:01
 * @description:
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    //根据月份集合,获取对应月份的会员人数
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private SetmealDao setmealDao;

    @Override
    public ArrayList<Integer> getmemberCountByMonth(ArrayList<String> months) {
        ArrayList<Integer> list = new ArrayList<>();
        for (String month : months) {
            Integer count = memberDao.getmemberCountByMonth(month);
            list.add(count);
        }
        return list;
    }

    //获取套餐统计圆形图数据
    @Override
    public Result getSetmealReport() {
        //先获取所有的套餐名
        try {
            List<Setmeal> all = setmealDao.getAll();

            //在用套餐名获取对应的套餐数量
            ArrayList<String> setmealNames = new ArrayList<>();
            ArrayList<Map> setmealCount = new ArrayList<>();
            for (Setmeal setmeal : all) {
                String name = setmeal.getName();
                //把套餐名存进套餐名集合
                setmealNames.add(name);

                Integer value = orderDao.getCountBySetmealId(setmeal.getId());
                //把套餐名和订单数存进数据集合
                HashMap<String, Object> valueAndName = new HashMap<>();
                valueAndName.put("name",name);
                valueAndName.put("value",value);
                setmealCount.add(valueAndName);

            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("setmealCount",setmealCount);
            map.put("setmealNames",setmealNames);
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }

    }

    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
        // 获得当前日期
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        // 获得本周一的日期
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        // 获取本周最后一天的日期
        String thisWeekSunday = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());
        // 获得本月第一天的日期
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        // 获取本月最后一天的日期
        String lastDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth());


        // 今日新增会员数
        Integer todayNewMember = memberDao.findMemberCountByDate(today);

        // 总会员数
        Integer totalMember = memberDao.findMemberTotalCount();

        // 本周新增会员数
        Integer thisWeekNewMember = memberDao.findMemberCountAfterDate(thisWeekMonday);

        // 本月新增会员数
        Integer thisMonthNewMember = memberDao.findMemberCountAfterDate(firstDay4ThisMonth);

        // 今日预约数
        Integer todayOrderNumber = orderDao.findOrderCountByDate(today);

        // 本周预约数
        Map<String,Object> weekMap = new HashMap<String,Object>();
        weekMap.put("begin",thisWeekMonday);
        weekMap.put("end",thisWeekSunday);
        Integer thisWeekOrderNumber = orderDao.findOrderCountBetweenDate(weekMap);

        // 本月预约数
        Map<String,Object> monthMap = new HashMap<String,Object>();
        monthMap.put("begin",firstDay4ThisMonth);
        monthMap.put("end",lastDay4ThisMonth);
        Integer thisMonthOrderNumber = orderDao.findOrderCountBetweenDate(monthMap);

        // 今日到诊数
        Integer todayVisitsNumber = orderDao.findVisitsCountByDate(today);

        // 本周到诊数
        Map<String,Object> weekMap2 = new HashMap<String,Object>();
        weekMap2.put("begin",thisWeekMonday);
        weekMap2.put("end",thisWeekSunday);
        Integer thisWeekVisitsNumber = orderDao.findVisitsCountAfterDate(weekMap2);

        // 本月到诊数
        Map<String,Object> monthMap2 = new HashMap<String,Object>();
        monthMap2.put("begin",firstDay4ThisMonth);
        monthMap2.put("end",lastDay4ThisMonth);
        Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(monthMap2);

        // 热门套餐（取前4）
        List<Map> hotSetmeal = orderDao.findHotSetmeal();







        Map<String,Object> result = new HashMap<>();
        result.put("reportDate",today);
        result.put("todayNewMember",todayNewMember);
        result.put("totalMember",totalMember);
        result.put("thisWeekNewMember",thisWeekNewMember);
        result.put("thisMonthNewMember",thisMonthNewMember);
        result.put("todayOrderNumber",todayOrderNumber);
        result.put("thisWeekOrderNumber",thisWeekOrderNumber);
        result.put("thisMonthOrderNumber",thisMonthOrderNumber);
        result.put("todayVisitsNumber",todayVisitsNumber);
        result.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        result.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        result.put("hotSetmeal",hotSetmeal);

        return result;

    }
}
