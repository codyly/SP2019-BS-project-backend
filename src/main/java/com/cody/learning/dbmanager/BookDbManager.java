package com.cody.learning.dbmanager;

import java.util.List;

import com.cody.learning.objects.Book;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookDbManager extends MongoRepository<Book, Integer> {
    List<Book> findByAuthorContains(String author);
    Book findByNameEquals(String name);
    List<Book> findByDescContains(String desc);
    List<Book> findByState(Double state);
}