package com.future.clockio.service.impl.core;

import com.future.clockio.entity.constant.Erole;
import com.future.clockio.entity.core.Role;
import com.future.clockio.entity.core.User;
import com.future.clockio.repository.core.RoleRepository;
import com.future.clockio.repository.core.UserRepository;
import com.future.clockio.service.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {
  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private PasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository,
                         RoleRepository roleRepository,
                         PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public User addUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  @PostConstruct
  private void setupDefaultUser() {
    if (userRepository.count() == 0) {
      Role roleAdmin = new Role(Erole.ROLE_ADMIN.toString());
      roleRepository.save(roleAdmin);
      userRepository.save(new User("admin", passwordEncoder.encode("admin"),
              roleAdmin));
    }
  }
}
