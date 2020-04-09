package com.my.health.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.my.health.constant.MessageConstant;
import com.my.health.constant.RedisMessageConstant;
import com.my.health.pojo.Member;
import com.my.health.pojo.Result;
import com.my.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-04-09 20:26
 * @description:
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService memberService;

    @RequestMapping("/check")
    public Result check(HttpServletResponse response, @RequestBody Map loginInfo) {
        try {
            String telephone = (String) loginInfo.get("telephone");
            String validateCode = (String) loginInfo.get("validateCode");

            Jedis jedis = jedisPool.getResource();
            String saveTelephone = jedis.get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
            boolean equals = StringUtils.isEquals(validateCode, saveTelephone);
            if (equals) {
                //判断这个用户是否存在
                //如果不在,就创建出来
                Member member = memberService.findByTelephone(telephone);
                if (member == null) {
                    member=new Member();
                    member.setPhoneNumber(telephone);
                    member.setRegTime(new Date());
                    memberService.addMember(member);
                }
                Cookie cookie = new Cookie("login_member_telephone", telephone);
                cookie.setPath("/");
                cookie.setMaxAge(60 * 60 * 24 * 30);
                response.addCookie(cookie);
            }

            return new Result(equals, equals ? MessageConstant.LOGIN_SUCCESS : MessageConstant.LOGIN_FAIL);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.LOGIN_ERROR);
        }
    }
}
















