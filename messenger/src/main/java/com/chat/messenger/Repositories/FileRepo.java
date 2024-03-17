package com.chat.messenger.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.chat.messenger.DocumentModels.Files;

public interface FileRepo extends MongoRepository<Files, String> {
    @Query("{$and:[{'from':?0,'to':?1},{'from':?1,'to':?0}]}")
    public List<Files> findBy(String from, String to);

}
