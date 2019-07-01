package com.cody.learning.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Locale.Category;
import java.util.regex.Pattern;

import com.cody.learning.dbmanager.BookDbManager;
import com.cody.learning.dbmanager.UserDbManager;
import com.cody.learning.objects.Book;
import com.cody.learning.objects.MarketResBody;
import com.cody.learning.objects.ResData;
import com.cody.learning.objects.User;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators.And;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController

public class BookController {
    @Autowired
    BookDbManager dbMgr;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserDbManager usermgr;

    @GetMapping("/books")
    public ModelAndView books() {
        List<Book> books = new ArrayList<>();
        Book b1 = new Book();
        b1.setAuthor("auth1");
        b1.setName("book1");
        b1.setPublicationDate(new Date().toString());
        Book b2 = new Book();
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
        b1.setAuthor("auth1");
        b1.setName("book1");
        b1.setPublicationDate(new Date().toString());
        return b1;
    }

    @GetMapping("/queryBook")
    public ResData queryBook(String owner, String id) {
        List<Book> booklist = mongoTemplate.find(new Query(Criteria.where("bookid").is(id)).addCriteria(Criteria.where("owner").is(owner)), Book.class);
        List<User> userlist = new ArrayList<>();
        List<String> tmpUserName = new ArrayList<>();
        Gson gson = new Gson();
        try {
            for(int i=0; i<booklist.size();i++){
                String name = booklist.get(i).getOwner();
                User user;
                if(tmpUserName.contains(name)){
                    user = userlist.get(tmpUserName.indexOf(name));
                }
                else{
                    user = usermgr.findByNameEquals(name);
                }
                userlist.add(user);
                tmpUserName.add(name);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        ResData resData = new ResData();
        String length = String.valueOf(booklist.size());
        resData.setStateCode(200);
        resData.setInfo(String.valueOf(length));
        MarketResBody marketResBody = new MarketResBody();
        marketResBody.setBooklist(booklist);
        marketResBody.setUserlist(userlist);
        resData.setMarketResBody(marketResBody);
        return resData;
    }

    @GetMapping(value = "/queryMarket")
    @ResponseBody
    public ResData queryMarket(String page) {
        Integer p = Integer.parseInt(page);
        List<Book> booklist = mongoTemplate.find(new Query(Criteria.where("state").lt(2.0)),Book.class);
        Integer pageNumber=booklist.size();
        
        booklist = booklist.subList(p * 24, Math.min(booklist.size()-1, p* 24 + 24));
        List<User> userlist = new ArrayList<>();
        List<String> tmpUserName = new ArrayList<>();
        Gson gson = new Gson();
        try {
            for(int i=0; i<booklist.size();i++){
                String name = booklist.get(i).getOwner();
                User user;
                if(tmpUserName.contains(name)){
                    user = userlist.get(tmpUserName.indexOf(name));
                }
                else{
                    user = usermgr.findByNameEquals(name);
                }
                userlist.add(user);
                tmpUserName.add(name);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        ResData resData = new ResData();
        // String length = String.valueOf(booklist.size());
        resData.setStateCode(200);
        resData.setInfo(String.valueOf(pageNumber));
        MarketResBody marketResBody = new MarketResBody();
        marketResBody.setBooklist(booklist);
        marketResBody.setUserlist(userlist);
        resData.setMarketResBody(marketResBody);
        return resData;
    }

    @GetMapping(value = "/queryCategory")
    @ResponseBody
    public ResData queryCategory(String page, String category) {
        Integer p = Integer.parseInt(page);
        Pattern pattern=Pattern.compile("^.*"+category+".*$", Pattern.CASE_INSENSITIVE);
        List<Book> booklist = mongoTemplate.find(new Query(Criteria.where("state").lt(2.0)).addCriteria(Criteria.where("desc").regex(pattern)),Book.class);
        Integer pageNumber=booklist.size();
        
        if(booklist.size() == 0){
            booklist = new ArrayList<>();
        }else{
            booklist = booklist.subList(p * 24, Math.min(booklist.size()-1, p* 24 + 24));
        }
        List<User> userlist = new ArrayList<>();
        List<String> tmpUserName = new ArrayList<>();
        Gson gson = new Gson();
        try {
            for(int i=0; i<booklist.size();i++){
                String name = booklist.get(i).getOwner();
                User user;
                if(tmpUserName.contains(name)){
                    user = userlist.get(tmpUserName.indexOf(name));
                }
                else{
                    user = usermgr.findByNameEquals(name);
                }
                userlist.add(user);
                tmpUserName.add(name);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        ResData resData = new ResData();
        // String length = String.valueOf(booklist.size());
        resData.setStateCode(200);
        resData.setInfo(String.valueOf(pageNumber));
        MarketResBody marketResBody = new MarketResBody();
        marketResBody.setBooklist(booklist);
        marketResBody.setUserlist(userlist);
        resData.setMarketResBody(marketResBody);
        return resData;
    }

    @GetMapping(value = "/queryToday")
    @ResponseBody
    public ResData queryToday(String page) {
        Integer p = Integer.parseInt(page);
        List<Book> booklist = mongoTemplate.find(new Query(Criteria.where("state").is(1.5)),Book.class);
        Integer pageNumber=booklist.size();
        System.out.println(pageNumber);
        if(booklist.size() == 0){
            booklist = new ArrayList<>();
        }else{
            booklist = booklist.subList(p * 24, Math.min(booklist.size(), p* 24 + 24));
        }
        List<User> userlist = new ArrayList<>();
        List<String> tmpUserName = new ArrayList<>();
        Gson gson = new Gson();
        try {
            for(int i=0; i<booklist.size();i++){
                String name = booklist.get(i).getOwner();
                User user;
                if(tmpUserName.contains(name)){
                    user = userlist.get(tmpUserName.indexOf(name));
                }
                else{
                    user = usermgr.findByNameEquals(name);
                }
                userlist.add(user);
                tmpUserName.add(name);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        ResData resData = new ResData();
        // String length = String.valueOf(booklist.size());
        resData.setStateCode(200);
        resData.setInfo(String.valueOf(pageNumber));
        MarketResBody marketResBody = new MarketResBody();
        marketResBody.setBooklist(booklist);
        marketResBody.setUserlist(userlist);
        resData.setMarketResBody(marketResBody);
        return resData;
    }

    @GetMapping("/mongoTl")
    public void mongTl() {
        List<Book> books = new ArrayList();
        Book b1 = new Book();
        b1.setAuthor("AA");
        b1.setName("book3");
        b1.setPublicationDate(new Date().toString());
        books.add(b1);
        Book b2 = new Book();
        b2.setAuthor("BB");
        b2.setName("book4");
        b2.setPublicationDate(new Date().toString());
        books.add(b2);
        mongoTemplate.insertAll(books);
        List<Book> booklist = mongoTemplate.findAll(Book.class);
        System.out.println(booklist);
        Book book = mongoTemplate.findById(3, Book.class);
        System.out.println(book);
    }

    @GetMapping("/addbooks")
    public String addbooks(){
        String data = sendGet("http://47.106.34.252/books.json", "");
        System.out.println(data);
        Gson gson = new Gson();
        List<Book> list = gson.fromJson(data, new TypeToken<List<Book>>(){}.getType());
        dbMgr.insert(list);
        return "success";
    }

    public String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


}
