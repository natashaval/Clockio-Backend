package com.future.clockio.repository;


import com.future.clockio.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
  User findByUsername(String username);
}
