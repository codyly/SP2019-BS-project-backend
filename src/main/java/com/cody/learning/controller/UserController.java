package com.cody.learning.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cody.learning.dbmanager.BookDbManager;
import com.cody.learning.dbmanager.UserDbManager;
import com.cody.learning.objects.Book;
import com.cody.learning.objects.MarketResBody;
import com.cody.learning.objects.ResData;
import com.cody.learning.objects.User;
import com.cody.learning.util.FileToBase64;
import com.google.gson.Gson;

import org.bson.BasicBSONDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import org.apache.commons.codec.digest.*;

@RestController

@RequestMapping("/user")
public class UserController {
   
    @Autowired
    UserDbManager dbMgr;

    @Autowired
    BookDbManager bookdbMgr;

    @Autowired
    MongoTemplate mongoTemplate;

    @GetMapping(value = "/name={name}&pwd={pwd}")
    public String user(@PathVariable String name, @PathVariable String pwd) {
        return name + pwd;
    }

    @PostMapping(value = "/login")
    public ResData login(@RequestBody String entity) {
        Gson gson = new Gson();
        User user = gson.fromJson(entity, User.class);
        
        User raw = dbMgr.findByNameEquals(user.getName());
        final BASE64Decoder decoder = new BASE64Decoder();
        try{
            user.setPwd(new String(decoder.decodeBuffer(user.getPwd()), "UTF-8"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        String responseText = "{stateCode: -1, info: \"fail\"}";
        if(user.getPwd().equals(raw.getPwd())){
            responseText = "{stateCode: 0, info:" +user.getName()+"}";
        }
         
        ResData resData = gson.fromJson(responseText, ResData.class);
        return resData;
    }

    @PostMapping(value = "/registry")
    public ResData registry(@RequestBody String entity) {
        System.out.println(entity);
        Gson gson = new Gson();
        User user = gson.fromJson(entity, User.class);
        System.out.println(user.getEmail());
        final BASE64Decoder decoder = new BASE64Decoder();
        try{
            user.setPwd(new String(decoder.decodeBuffer(user.getPwd()), "UTF-8"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        System.out.println(user.getPwd());
        Date timestamp = new Date();
        String combine = timestamp.toString()+user.getName();
        user.setUserid(	DigestUtils.sha1Hex(combine));
        user.setAuthority(2);
        user.setAvatar("http://47.106.34.252/avatar.png");
        user.setCoins(100.0f);
        user.setExp(50);
        user.setLevel(1);
        user.setFavorites("[]");
        user.setFriends("[]");
        user.setTempoMsg("[]");
        user.setCart("[]");
        user.setOrder("[]");
        user.setSellorder("[]");
        System.out.println(user.getUserid());
        User error = dbMgr.insert(user);
        String responseText = "{stateCode: -1, info: \"failed\"}";
        if(error !=null){
            responseText = "{stateCode: 0, info: \"success\"}";
        }
        ResData resData = gson.fromJson(responseText, ResData.class);
        return resData;
    }   
    

    @GetMapping("/addToCart")
    @ResponseBody
    public ResData addToCart(String username, String bookid, String owner){
        String jsonStr = "{'bookid':'"+bookid+"','owner':'"+owner+"'}";
        User user = mongoTemplate.findOne(new Query(Criteria.where("name").is(username)), User.class );
        if(!user.getCart().contains(jsonStr)){
            jsonStr = "[" + jsonStr + "," + user.getCart().substring(Math.min(1, user.getCart().length()),user.getCart().length());
            mongoTemplate.updateFirst(
                new Query(Criteria.where("name").is(username)), 
                new Update().set("cart", jsonStr), 
                User.class);
        }
        Integer c = jsonStr.split("bookid").length;
        ResData resData = new ResData();
        resData.setStateCode(200);
        resData.setInfo(String.valueOf(c));
        return resData;
    }

    @GetMapping("/removeFromCart")
    @ResponseBody
    public ResData removeFromCart(String username, String bookid, String owner){
        String jsonStr = "{'bookid':'"+bookid+"','owner':'"+owner+"'}";
        User user = mongoTemplate.findOne(new Query(Criteria.where("name").is(username)), User.class );
        if(user.getCart().contains(jsonStr)){
            String jsonStr2 = user.getCart();
            String front = jsonStr2.substring(0, jsonStr2.indexOf(jsonStr));
            String back = jsonStr2.substring(jsonStr2.indexOf(jsonStr)+jsonStr.length()+1);
            jsonStr = front+back;
            mongoTemplate.updateFirst(
                new Query(Criteria.where("name").is(username)), 
                new Update().set("cart", jsonStr), 
                User.class);
        }
        Integer c = jsonStr.split("bookid").length;
        ResData resData = new ResData();
        resData.setStateCode(200);
        resData.setInfo(String.valueOf(c));
        return resData;
    }

    @GetMapping("/getCart")
    @ResponseBody
    public ResData getCart(String username){
        User user = mongoTemplate.findOne(new Query(Criteria.where("name").is(username)), User.class );
        ResData resData = new ResData();
        resData.setStateCode(200);
        resData.setInfo(user.getCart());
        return resData;
    }

    @GetMapping("/getUser")
    @ResponseBody
    public ResData getUser(String username){
        List<User> user = mongoTemplate.find(new Query(Criteria.where("name").is(username)), User.class );
        ResData resData = new ResData();
        resData.setStateCode(200);
        MarketResBody marketResBody = new MarketResBody();
        marketResBody.setUserlist(user);
        resData.setMarketResBody(marketResBody);
        return resData;
    }

    @GetMapping("/addToFav")
    @ResponseBody
    public ResData addToFav(String username, String bookid, String owner){
        String jsonStr = "{'bookid':'"+bookid+"','owner':'"+owner+"'}";
        User user = mongoTemplate.findOne(new Query(Criteria.where("name").is(username)), User.class );
        if(!user.getFavorites().contains(jsonStr)){
            jsonStr = "[" + jsonStr + "," + user.getFavorites().substring(Math.min(1, user.getFavorites().length()),user.getFavorites().length());
            mongoTemplate.updateFirst(
                new Query(Criteria.where("name").is(username)), 
                new Update().set("favorites", jsonStr), 
                User.class);
        }

        Integer c = jsonStr.split(",").length;
        ResData resData = new ResData();
        resData.setStateCode(200);
        resData.setInfo(String.valueOf(c));
        return resData;
    }

    @GetMapping("/removeFromFav")
    @ResponseBody
    public ResData removeFromFav(String username, String bookid, String owner){
        String jsonStr = "{'bookid':'"+bookid+"','owner':'"+owner+"'}";
        User user = mongoTemplate.findOne(new Query(Criteria.where("name").is(username)), User.class );
        if(user.getFavorites().contains(jsonStr)){
            String jsonStr2 = user.getFavorites();
            String front = jsonStr2.substring(0, jsonStr2.indexOf(jsonStr));
            String back = jsonStr2.substring(jsonStr2.indexOf(jsonStr)+jsonStr.length()+1);
            jsonStr = front+back;
            mongoTemplate.updateFirst(
                new Query(Criteria.where("name").is(username)), 
                new Update().set("favorites", jsonStr), 
                User.class);
        }
        Integer c = jsonStr.split("bookid").length;
        ResData resData = new ResData();
        resData.setStateCode(200);
        resData.setInfo(String.valueOf(c));
        return resData;
    }

    @GetMapping("/getFav")
    @ResponseBody
    public ResData getFav(String username){
        User user = mongoTemplate.findOne(new Query(Criteria.where("name").is(username)), User.class );
        ResData resData = new ResData();
        resData.setStateCode(200);
        resData.setInfo(user.getFavorites());
        return resData;
    }

    @GetMapping("/buyBook")
    @ResponseBody
    public ResData buyBook(String username, String bookid, String owner, String price){
        double priced = Double.parseDouble(price);
        String jsonStr = "{'bookid':'"+bookid+"','owner':'"+owner+"','buyer':'"+username+"'}";
        User buyer = mongoTemplate.findOne(new Query(Criteria.where("name").is(username)), User.class );
        double coin1 = buyer.getCoins()-priced;
        
        User seller = mongoTemplate.findOne(new Query(Criteria.where("name").is(owner)), User.class );
        // Book book = mongoTemplate.findOne(new Query(Criteria.where("bookid").is(bookid)).addCriteria(Criteria.where("owner").is(owner)), Book.class);
        String jsonStr1 =buyer.getOrder();
        String jsonStr2 = seller.getSellorder();
        jsonStr1 = "[" + jsonStr + "," + jsonStr1.substring(Math.min(1, jsonStr1.length()),jsonStr1.length());
        jsonStr2 = "[" + jsonStr + "," + jsonStr2.substring(Math.min(1, jsonStr2.length()),jsonStr2.length());
        double coin2 = seller.getCoins()+priced;
        mongoTemplate.updateFirst(
            new Query(Criteria.where("name").is(username)), 
            new Update().set("order", jsonStr1), 
            User.class);
        mongoTemplate.updateFirst(
            new Query(Criteria.where("name").is(username)), 
            new Update().set("coins", String.valueOf(coin1)), 
            User.class);
        mongoTemplate.updateFirst(
            new Query(Criteria.where("name").is(owner)), 
            new Update().set("sellorder", jsonStr2), 
            User.class);
        mongoTemplate.updateFirst(
            new Query(Criteria.where("name").is(owner)), 
            new Update().set("coins", String.valueOf(coin2)), 
            User.class);    
        mongoTemplate.updateFirst(
            new Query(Criteria.where("bookid").is(bookid)).addCriteria(Criteria.where("owner").is(owner)),
            new Update().set("state", 3), 
            Book.class);
        Integer c = jsonStr.split(",").length;
        ResData resData = new ResData();
        resData.setStateCode(200);
        resData.setInfo(String.valueOf(c));
        return resData;
    }
}
