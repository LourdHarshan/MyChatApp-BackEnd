package com.chat.messenger.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.chat.messenger.DocumentModels.User;

public interface UserRepo extends MongoRepository<User, String> {

    @Query("{userName:?0,password:?1}")
    public User findBy(String userName, String password);

    @Query("{userName:{$ne:?0}}")
    public List<User> findAllBy(String userName);

}
