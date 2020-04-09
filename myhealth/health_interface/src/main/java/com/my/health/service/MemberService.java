package com.my.health.service;

import com.my.health.pojo.Member;

/**
 * @Author: Deng
 * @date: 2020-04-09 21:00
 * @description:
 */
public interface MemberService {
    Member findByTelephone(String telephone);

    void addMember(Member member);
}
