package com.cody.learning.dbmanager;

import java.util.List;

import com.cody.learning.objects.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDbManager extends MongoRepository<User, Integer> {
    List<User> findByNameContains(String name);

    User findByNameEquals(String name);
}