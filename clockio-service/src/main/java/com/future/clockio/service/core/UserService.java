package com.future.clockio.service.core;

import com.future.clockio.entity.core.User;
import com.future.clockio.request.core.UserRequest;

import java.util.UUID;

public interface UserService {
  User findById(UUID userId);
  User saveUser(User user);
  User addUser(UserRequest request);
}
