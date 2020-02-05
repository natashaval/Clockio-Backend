package com.future.clockio.controller.helper;

import com.future.clockio.entity.company.Employee;
import com.future.clockio.entity.constant.Erole;
import com.future.clockio.entity.core.Role;
import com.future.clockio.entity.core.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class EntityMock {
  public static final double DOUBLE_RANDOM = new Random().nextDouble();
  private static final Date DATE_NOW = new Date();

  // User
  public static final Role ROLE = new Role(1, Erole.ROLE_ADMIN.toString());
  public static final UUID USER_ID = UUID.randomUUID();
  public static final String USER_USERNAME = "User";
  public static final String USER_PASSWORD = "Pass";
  private static final String USER_PASSWORD_BCRYPT = new BCryptPasswordEncoder().encode(USER_PASSWORD);
  public static final User USER = new User(USER_ID, USER_USERNAME, USER_PASSWORD_BCRYPT, null,
          ROLE, true, true, true, true);

  // Employee
  public static final UUID EMP_ID = UUID.randomUUID();
  public static final String EMP_NAME = "Employee";
  public static final Employee EMPLOYEE =
          Employee.builder().id(EMP_ID).firstName(EMP_NAME).lastName(EMP_NAME).build();
}
