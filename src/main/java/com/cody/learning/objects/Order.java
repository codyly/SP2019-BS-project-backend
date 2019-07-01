package com.cody.learning.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Order {
    private String orderid;
    private Book object;
    private User userTo;
    private User userFrom;
    private Date time;
    private Float amount;
    private Integer type;
    private Integer state;
    private List<String> infoSet;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Book getObject() {
        return object;
    }

    public void setObject(Book object) {
        this.object = object;
    }

    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<String> getInfoSet() {
        return infoSet;
    }

    public void setInfoSet(List<String> infoSet) {
        this.infoSet = infoSet;
    }
}
