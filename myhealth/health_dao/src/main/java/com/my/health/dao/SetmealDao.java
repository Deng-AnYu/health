package com.my.health.dao;

import com.my.health.pojo.CheckGroup;
import com.my.health.pojo.Setmeal;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: Deng
 * @date: 2020-04-04 20:45
 * @description:
 */
public interface SetmealDao {
    void addSetmeal(Setmeal setmeal);

    void addLinked(HashMap<String, Integer> map);

    List<Setmeal> selectByCondition(String queryString);

    void deleteLinked(Integer id);

    void deleteById(Integer id);

    Integer[] findSetmealLinkedGroup(Integer id);

    Setmeal getSetmealById(Integer id);

    void editSetmeal(Setmeal setmeal);

    List<Setmeal> getAll();

    Setmeal getById(Integer id);

    List<CheckGroup> getSetmealLinkedGroups(Integer id);
}
