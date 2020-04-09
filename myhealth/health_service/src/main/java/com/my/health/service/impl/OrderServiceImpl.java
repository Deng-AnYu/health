package com.my.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.my.health.constant.MessageConstant;
import com.my.health.dao.MemberDao;
import com.my.health.dao.OrderDao;
import com.my.health.dao.OrderSettingDao;
import com.my.health.pojo.Member;
import com.my.health.pojo.OrderSetting;
import com.my.health.pojo.Result;
import com.my.health.service.OrderService;
import com.my.health.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-04-09 16:40
 * @description:
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderSettingDao orderSettingDao;


    @Override
    public Result order(Map orderInfo) throws Exception {
        String orderDate = (String)orderInfo.get("orderDate");//预约日期
        String telephone = (String)orderInfo.get("telephone");//手机号码
        String setmealId = (String)orderInfo.get("setmealId");//套餐id
        String name = (String)orderInfo.get("name");//姓名
        String sex = (String)orderInfo.get("sex");//性别
        String idCard = (String)orderInfo.get("idCard");//身份证
        String orderType = (String)orderInfo.get("orderType");//预约方式


        Date date = DateUtils.parseString2Date(orderDate);
        orderInfo.put("orderDate",date);

        //查询是否有这个日期
        int isExist = orderSettingDao.isExist(date);
        if (isExist<1){
            return new Result(false,MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //查询那个日期是否还有号
        int isIdle=orderDao.isIdle(date);
        if (isIdle<=0){
            return new Result(false,MessageConstant.ORDER_FULL);
        }
        //检查这个账号是否存在
            //用手机号码确认
        Member member=memberDao.getByTelephone(telephone);
        //如果账号存在
        if (member!=null){
            //检查是否有重复预约
                //当日期.套餐.账号都重复时,就是重复预约
            Integer memberId = member.getId();
            orderInfo.put("memberId",memberId);
            int isAlready=orderDao.isAlready(orderInfo);
            if (isAlready>0){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
            //通过这么多验证,现在可以预约了
            Integer id=orderDao.addOrder(orderInfo);
            return new Result(true,MessageConstant.ORDER_SUCCESS,id);
        }
        //执行到这里,说明账号不存在,就注册一个并预约一个号
        member = new Member();
        member.setName(name);//会员名称
        member.setSex(sex);//性别
        member.setIdCard(idCard);//身份证
        member.setPhoneNumber(telephone);//手机号码
        member.setRegTime(new Date());//注册时间
        memberDao.addMember(member);

        orderInfo.put("memberId",member.getId());
        orderDao.addOrder(orderInfo);

        return new Result(true,MessageConstant.ORDER_SUCCESS,orderInfo.get("id"));
    }

    @Override
    public Map findById(Integer id) {
        return orderDao.getDataForSuccess(id);
    }


}
