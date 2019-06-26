package com.cody.learning;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Book {
    private Integer id;
    private String name;
    private String author;
    @JsonIgnore
    private Float price;

    @JsonFormat(pattern = "yyyy-MM-dd")
    protected Date publicationDate;

    public void setPublicationDate(Date date) {
        this.publicationDate = date;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }
}
