package com.cody.learning;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cody.learning.dbmanager.BookDbManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class BookController {
    @Autowired
    BookDbManager dbMgr;

    @GetMapping("/books")
    public ModelAndView books() {
        List<Book> books = new ArrayList<>();
        Book b1 = new Book();
        b1.setId(1);
        b1.setAuthor("auth1");
        b1.setName("book1");
        b1.setPublicationDate(new Date());
        Book b2 = new Book();
        b2.setId(1);
        b2.setAuthor("auth2");
        b2.setName("book2");
        books.add(b1);
        books.add(b2);
        ModelAndView mv = new ModelAndView();
        mv.addObject("books", books);
        mv.setViewName("books");
        return mv;
    }

    @GetMapping("/book")
    public Book book() {
        Book b1 = new Book();
        b1.setId(1);
        b1.setAuthor("auth1");
        b1.setName("book1");
        b1.setPublicationDate(new Date());
        return b1;
    }

    @GetMapping("/test1")
    public void test1() {
        List<Book> books = new ArrayList();
        Book b1 = new Book();
        b1.setId(1);
        b1.setAuthor("A");
        b1.setName("book1");
        b1.setPublicationDate(new Date());
        books.add(b1);
        Book b2 = new Book();
        b2.setId(2);
        b2.setAuthor("B");
        b2.setName("book2");
        b2.setPublicationDate(new Date());
        books.add(b2);
        dbMgr.insert(books);
        List<Book> booklist = dbMgr.findByAuthorContains("A");
        System.out.println(booklist);
        Book book = dbMgr.findByNameEquals("book1");
        System.out.println(book);
    }

    @GetMapping("/query")
    public void query() {
        List<Book> booklist = dbMgr.findByAuthorContains("A");
        System.out.println(booklist);
        Book book = dbMgr.findByNameEquals("book1");
        System.out.println(book);
    }

    @Autowired
    MongoTemplate mongoTemplate;

    @GetMapping("/mongoTl")
    public void mongTl() {
        List<Book> books = new ArrayList();
        Book b1 = new Book();
        b1.setId(3);
        b1.setAuthor("AA");
        b1.setName("book3");
        b1.setPublicationDate(new Date());
        books.add(b1);
        Book b2 = new Book();
        b2.setId(4);
        b2.setAuthor("BB");
        b2.setName("book4");
        b2.setPublicationDate(new Date());
        books.add(b2);
        mongoTemplate.insertAll(books);
        List<Book> booklist = mongoTemplate.findAll(Book.class);
        System.out.println(booklist);
        Book book = mongoTemplate.findById(3, Book.class);
        System.out.println(book);
    }

}
