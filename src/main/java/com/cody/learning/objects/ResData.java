package com.cody.learning.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ResData {
    private Integer stateCode;
    private String info;
    private MarketResBody marketResBody;

    public com.cody.learning.objects.MarketResBody getMarketResBody() {
        return marketResBody;
    }

    public void setMarketResBody(com.cody.learning.objects.MarketResBody marketResBody) {
        this.marketResBody = marketResBody;
    }

    public Integer getStateCode() {
        return stateCode;
    }

    public void setStateCode(Integer stateCode) {
        this.stateCode = stateCode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
