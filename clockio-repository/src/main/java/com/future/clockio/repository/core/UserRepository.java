package com.future.clockio.repository.core;


import com.future.clockio.entity.core.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
  User findByUsername(String username);
}
