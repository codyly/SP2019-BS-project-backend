package com.cody.learning.objects;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MessageList {
    private List<Message> list;

    public List<Message> getList() {
        return list;
    }

    public void setList(List<Message> list) {
        this.list = list;
    }
}
