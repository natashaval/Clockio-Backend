package com.future.clockio.service.impl.core;

import com.future.clockio.entity.constant.Erole;
import com.future.clockio.entity.core.Role;
import com.future.clockio.entity.core.User;
import com.future.clockio.exception.InvalidRequestException;
import com.future.clockio.repository.core.RoleRepository;
import com.future.clockio.repository.core.UserRepository;
import com.future.clockio.request.core.UserRequest;
import com.future.clockio.service.core.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
  @Mock
  private UserRepository userRepository;
  @Mock
  private RoleRepository roleRepository;
  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserServiceImpl userService;

  private static final UserRequest USER_REQUEST = UserRequest.builder()
          .username("user")
          .password("pass")
          .roleId(1)
          .build();
  private static final Role ROLE = new Role(1, Erole.ROLE_ADMIN.toString());
  private static final UUID USER_ID = UUID.randomUUID();
  private static final String USER_PASSWORD = new BCryptPasswordEncoder().encode("pass");
  private static final User USER = new User(USER_ID, "user", USER_PASSWORD, null,
          ROLE, true, true, true, true);

  @Test
  public void addUser_roleNotFound() {
    UserRequest request = UserRequest.builder().username("test").password("pass").build();
    try {
      userService.addUser(request);
    } catch (InvalidRequestException e) {
      Assert.assertEquals("Role not found!", e.getMessage());
    }
  }

  @Test
  public void addUser_success() {
    when(roleRepository.findById(anyInt())).thenReturn(java.util.Optional.of(ROLE));
    when(userRepository.save(any(User.class))).thenReturn(USER);
    User result = userService.addUser(USER_REQUEST);

    Assert.assertEquals(USER_ID, result.getId());
    Assert.assertEquals("user", result.getUsername());

  }
}
