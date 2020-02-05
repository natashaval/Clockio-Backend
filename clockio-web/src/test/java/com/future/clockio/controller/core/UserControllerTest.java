package com.future.clockio.controller.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.clockio.entity.constant.Erole;
import com.future.clockio.request.core.UserRequest;
import com.future.clockio.service.core.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.future.clockio.controller.helper.EntityMock.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
  private MockMvc mvc;

  @Spy private ObjectMapper mapper;
  @Mock private UserService userService;
  @InjectMocks private UserController controller;
  private static final String USER_API = "/api/user";

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  public void createUser() throws Exception {
    UserRequest request = new UserRequest(USER_USERNAME, USER_PASSWORD, 1);
    when(userService.addUser(any(UserRequest.class))).thenReturn(USER);
    mvc.perform(post(USER_API)
    .contentType(MediaType.APPLICATION_JSON)
    .content(mapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username", is(USER_USERNAME)))
            .andExpect(jsonPath("$.role.id", is(1)))
            .andExpect(jsonPath("$.role.role", is(Erole.ROLE_ADMIN.toString())));
  }

}
