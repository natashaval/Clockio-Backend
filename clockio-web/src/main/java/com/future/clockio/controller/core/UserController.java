package com.future.clockio.controller.core;

import com.future.clockio.entity.core.User;
import com.future.clockio.request.core.UserRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping
  public User createUser(@RequestBody UserRequest request) {
    return userService.addUser(request);
  }
}
