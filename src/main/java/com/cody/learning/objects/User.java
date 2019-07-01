package com.cody.learning.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class User {
    private String userid;
    private String name;
    private String pwd;
    private String email;
    private String tradepwd;
    private Integer authority;
    private String avatar;
    private Integer level;
    private Integer exp;
    private Float coins;
    private String favorites;
    private String cart;
    private String own;
    private String order;
    private String friends;
    private String tempoMsg;
    private String sellorder;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getTradepwd() {
        return tradepwd;
    }

    public void setTradepwd(String tradepwd) {
        this.tradepwd = tradepwd;
    }

    public Integer getAuthority() {
        return authority;
    }

    public void setAuthority(Integer authority) {
        this.authority = authority;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Float getCoins() {
        return coins;
    }

    public void setCoins(Float coins) {
        this.coins = coins;
    }

    public String getFavorites() {
        return favorites;
    }

    public void setFavorites(String favorites) {
        this.favorites = favorites;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOwn() {
        return own;
    }

    public void setOwn(String own) {
        this.own = own;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }
    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }

    public String getTempoMsg() {
        return tempoMsg;
    }

    public void setTempoMsg(String tempoMsg) {
        this.tempoMsg = tempoMsg;
    }

    public String getSellorder() {
        return sellorder;
    }

    public void setSellorder(String sellorder) {
        this.sellorder = sellorder;
    }
}
