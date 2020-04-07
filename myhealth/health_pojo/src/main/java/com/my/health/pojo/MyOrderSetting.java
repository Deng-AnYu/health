package com.my.health.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约设置
 */
public class MyOrderSetting implements Serializable{
    private Integer id ;
    private Date orderDate;//预约设置日期
    private int number;//可预约人数
    private int reservations ;//已预约人数
    private int date;
    public MyOrderSetting() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
        this.date=orderDate.getDate();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getReservations() {
        return reservations;
    }

    public void setReservations(int reservations) {
        this.reservations = reservations;
    }

    public int getDate() {
        return orderDate.getDate();
    }

    public void setDate(int date) {
        this.date = date;
    }
}
