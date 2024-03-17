package com.chat.messenger.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.chat.messenger.DocumentModels.Message;

public interface MessageRepo extends MongoRepository<Message, String> {
    @Query("{$or:[{'from':?0,'to':?1},{'from':?1,'to':?0}]}")
    public List<Message> findBy(String from, String to);

}
