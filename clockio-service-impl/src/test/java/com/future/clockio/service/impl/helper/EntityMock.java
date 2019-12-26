package com.future.clockio.service.impl.helper;

import com.future.clockio.entity.company.Branch;
import com.future.clockio.entity.company.Department;
import com.future.clockio.entity.company.Employee;
import com.future.clockio.entity.constant.Erole;
import com.future.clockio.entity.core.Role;
import com.future.clockio.entity.core.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Random;
import java.util.UUID;

public class EntityMock {
  // Branch
  public static final UUID BRANCH_ID = UUID.randomUUID();
  public static final String BRANCH_NAME = "Technology";
  public static final double DOUBLE_RANDOM = new Random().nextDouble();
  public static final Branch BRANCH = new Branch(BRANCH_ID, BRANCH_NAME, null,
          null, DOUBLE_RANDOM, DOUBLE_RANDOM);

  //Department
  public static final UUID DEPT_ID = UUID.randomUUID();
  public static final String DEPT_NAME = "Department";
  public static final Department DEPARTMENT = Department.builder()
          .id(DEPT_ID)
          .name(DEPT_NAME)
          .branchId(BRANCH_ID.toString()).build();

  // Employee
  public static final UUID EMP_ID = UUID.randomUUID();
  public static final String EMP_NAME = "Employee";
  public static final Employee EMPLOYEE =
          Employee.builder().id(EMP_ID).firstName(EMP_NAME).lastName(EMP_NAME).build();

  // User
  public static final Role ROLE = new Role(1, Erole.ROLE_ADMIN.toString());
  public static final UUID USER_ID = UUID.randomUUID();
  private static final String USER_PASSWORD = new BCryptPasswordEncoder().encode("pass");
  public static final User USER = new User(USER_ID, "user", USER_PASSWORD, null,
          ROLE, true, true, true, true);
}
