package com.future.clockio.service.core;

import com.future.clockio.entity.core.User;
import com.future.clockio.request.core.UserRequest;

public interface UserService {
  User addUser(UserRequest request);
}
