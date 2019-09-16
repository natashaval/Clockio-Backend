package com.future.clockio.service.impl;

import com.future.clockio.entity.User;
import com.future.clockio.repository.UserRepository;
import com.future.clockio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  public User addUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  @PostConstruct
  private void setupDefaultUser() {
    if (userRepository.count() == 0) {
      userRepository.save(new User("admin", passwordEncoder.encode("admin"),
              Arrays.asList("ROLE_ADMIN", "ROLE_USER")));
    }
  }
}
