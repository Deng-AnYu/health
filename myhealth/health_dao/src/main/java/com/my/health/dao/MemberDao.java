package com.my.health.dao;

import com.my.health.pojo.Member;

import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-04-09 17:51
 * @description:
 */
public interface MemberDao {
    int isExist(String telephone);

    Member getByTelephone(String telephone);

    void addMember(Member member);

    Integer getmemberCountByMonth(String month);

    public Integer findMemberCountBeforeDate(String date);
    public Integer findMemberCountByDate(String date);
    public Integer findMemberCountAfterDate(String date);
    public Integer findMemberTotalCount();

}
