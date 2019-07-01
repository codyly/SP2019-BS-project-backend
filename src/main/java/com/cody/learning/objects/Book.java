package com.cody.learning.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Book {
    private String bookid;
    private String name;
    private String baseCover;
    // private List<String> basePic;
    private String author;
    private String owner;
    private String desc;
    private Double price;
    private Double state;
    private String isbn;
    private Double copies;




    // @JsonFormat(pattern = "yyyy-MM-dd")
    private String publicationDate;
    private String updateDate;

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getBaseCover() {
        return baseCover;
    }

    public void setBaseCover(String baseCover) {
        this.baseCover = baseCover;
    }

    // public List<String> getBasePic() {
    //     return basePic;
    // }

    // public void setBasePic(List<String> basePic) {
    //     this.basePic = basePic;
    // }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getState() {
        return state;
    }

    public Double getCopies() {
        return copies;
    }

    public void setState(Double state) {
        this.state = state;
    }

    public void setCopies(Double copies) {
        this.copies = copies;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public void setPublicationDate(String date) {
        this.publicationDate = date;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setId(String id) {
        this.bookid = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return bookid;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
