package com.future.clockio.controller.helper;

import com.future.clockio.entity.constant.Erole;
import com.future.clockio.entity.core.Role;
import com.future.clockio.entity.core.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

public class EntityMock {
  public static final Role ROLE = new Role(1, Erole.ROLE_ADMIN.toString());
  public static final UUID USER_ID = UUID.randomUUID();
  public static final String USER_USERNAME = "User";
  public static final String USER_PASSWORD = "Pass";
  private static final String USER_PASSWORD_BCRYPT = new BCryptPasswordEncoder().encode(USER_PASSWORD);
  public static final User USER = new User(USER_ID, USER_USERNAME, USER_PASSWORD_BCRYPT, null,
          ROLE, true, true, true, true);
}
