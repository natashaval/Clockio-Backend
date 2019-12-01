package com.future.clockio.request.core;

import lombok.Data;

@Data
public class UserRequest {
  private String username;
  private String password;
  private Integer roleId;
}
