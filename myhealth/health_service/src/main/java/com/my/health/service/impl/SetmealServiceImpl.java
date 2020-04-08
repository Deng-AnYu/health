package com.my.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.my.health.dao.CheckGroupDao;
import com.my.health.dao.CheckItemDao;
import com.my.health.pojo.*;
import com.my.health.dao.SetmealDao;
import com.my.health.service.SetmealService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Deng
 * @date: 2020-04-04 20:40
 * @description:
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private CheckGroupDao checkGroupDao;
    @Autowired
    private CheckItemDao checkItemDao;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    //    @Autowired
//    private FreeMarkerConfigurer configuration;
    @Value("${out_put_path}")
    private String outputpath;
    @Value("${mobile_stemeal}")
    private String mobile_stemeal;
   @Value("${mobile_stemeal_detail}")
    private String mobile_stemeal_detail;


    //添加套餐
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //先添加套餐
        setmealDao.addSetmeal(setmeal);
        //再循环关系表里添加套餐和检查组的关系
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("setmealId", setmeal.getId());
            for (Integer checkgroupId : checkgroupIds) {
                map.put("checkgroupId", checkgroupId);
                setmealDao.addLinked(map);
            }
        }
        //增加后,更新套餐列表页面
        staticSetmealListHtml();
        //还要更新这个套餐的套餐详情页面 todo
        staticSetmealDetails(setmeal.getId());
    }


    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());//当前页码  每页显示多少条记录
        List<Setmeal> setmealList =
                setmealDao.selectByCondition(queryPageBean.getQueryString());
        //封装分页PageInfo对象
        PageInfo<Setmeal> pageInfo = new PageInfo<>(setmealList);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());


    }

    //删除套餐
    @Override
    public void deleteById(Integer id, Integer[] checkgroupIds) {
        //先把关联删了,再把套餐删了
        setmealDao.deleteLinked(id);
        setmealDao.deleteById(id);

        staticSetmealListHtml();
        deleteStaticHtml(id);

    }

    @Override
    public Map findGroupAndLinkedItem(Integer id) {

        //根据id获取关联的检查项
        Integer[] ids = setmealDao.findSetmealLinkedGroup(id);
        //根据id获取这个检查组的信息
        Setmeal setmeal = setmealDao.getSetmealById(id);

        HashMap<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        map.put("setmeal", setmeal);
        return map;

    }

    //编辑套餐
    @Override
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
        //修改表
        setmealDao.editSetmeal(setmeal);
        //先删除原有的所有关系
        setmealDao.deleteLinked(setmeal.getId());
        //在重新建立一遍
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("setmealId", setmeal.getId());
            for (Integer checkgroupId : checkgroupIds) {
                map.put("checkgroupId", checkgroupId);
                setmealDao.addLinked(map);
            }
        }
        staticSetmealListHtml();
        staticSetmealDetails(setmeal.getId());


    }

    //移动端的方法,获取所有套餐
    @Override
    public List<Setmeal> getAll() {
        return setmealDao.getAll();
    }

    //移动端的方法
    //根据id获取套餐,并获取此套餐对应的检查组
    @Override
    public Setmeal getById(Integer id) {
        //获取套餐
        Setmeal setmeal = setmealDao.getById(id);
        //获取套餐中的检查组
        List<CheckGroup> groups = checkGroupDao.getSetmealLinkedGroups(id);
        //获取检查组中的检查项
        for (CheckGroup group : groups) {
            List<CheckItem> items = checkItemDao.getGroupLinkedItem(group.getId());
            group.setCheckItems(items);
        }
        setmeal.setCheckGroups(groups);
        return setmeal;
    }

    //更新套餐列表页面
    private void staticSetmealListHtml() {

        List<Setmeal> setmeals = setmealDao.getAll();
        HashMap<String, Object> map = new HashMap<>();
        map.put("setmealList", setmeals);
        //
        createStaticHtml("mobile_setmeal.ftl", map, mobile_stemeal);

    }

    //根据套餐id来更新这个套餐的套餐详情页面
    private void staticSetmealDetails(Integer id) {
        Setmeal setmeal = getById(id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("setmeal",setmeal);
        createStaticHtml("mobile_setmeal_detail.ftl",map,mobile_stemeal_detail+setmeal.getId());

    }

    //根据套餐id,删除静态页面
    private void deleteStaticHtml(Integer id) {
        File file = new File(outputpath + "\\" + mobile_stemeal_detail + id + ".html");
        if (file.exists()){
            file.delete();
        }
    }



    //静态化移动端的套餐列表页面
    //参数:
    //模板名
    //数据集合
    //静态化后的页面名
    private void createStaticHtml(String templateName, HashMap map, String htmlName) {
        BufferedWriter writer = null;
        try {
            //设置模板
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate(templateName);
            //设置输出流
            writer = new BufferedWriter(new FileWriter(outputpath + "\\" + htmlName + ".html"));
            //根据map和输出流,在输出流指定的位置生成静态化页面
            template.process(map, writer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}
