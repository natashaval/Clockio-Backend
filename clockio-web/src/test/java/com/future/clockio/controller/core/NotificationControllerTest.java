package com.future.clockio.controller.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.clockio.entity.core.Notification;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.core.NotificationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class NotificationControllerTest {
  private MockMvc mvc;

  @Spy
  private ObjectMapper mapper;

  @Mock
  private NotificationService notificationService;

  @InjectMocks
  private NotificationController controller;

  private static final String TITLE = "Title";
  private static final String CONTENT = "Content";
  private static final Notification NOTIFICATION = Notification.builder()
          .id(1L)
          .title(TITLE)
          .content(CONTENT)
          .build();
  private static final String NOTIF_API = "/api/notification";

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  public void findById() throws Exception {
    when(notificationService.findById(anyLong())).thenReturn(NOTIFICATION);
    mvc.perform(get(NOTIF_API + "/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.title", is(TITLE)))
            .andExpect(jsonPath("$.content", is(CONTENT)));
    verify(notificationService, times(1)).findById(anyLong());
  }

  @Test
  public void findAll() throws Exception {
    List<Notification> notifList = Collections.singletonList(NOTIFICATION);
    Page<Notification> notifPage = new PageImpl<>(notifList);
    when(notificationService.findAllPageable(anyInt(), anyInt())).thenReturn(notifPage);
    mvc.perform(get(NOTIF_API))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].title", is(TITLE)))
            .andExpect(jsonPath("$.content[0].content", is(CONTENT)));
    verify(notificationService, times(1)).findAllPageable(anyInt(), anyInt());
  }

  @Test
  public void createNotif() throws Exception {
    when(notificationService.createNotification(any(Notification.class)))
            .thenReturn(BaseResponse.success("Notification created!"));
    mvc.perform(post(NOTIF_API)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(NOTIFICATION)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", is(true)))
            .andExpect(jsonPath("$.message", is("Notification created!")));
    verify(notificationService, times(1)).createNotification(any());
  }

  @Test
  public void updateNotif() throws Exception {
    when(notificationService.updateNotification(anyLong(), any(Notification.class)))
            .thenReturn(BaseResponse.success("Notification updated!"));
    mvc.perform(put(NOTIF_API + "/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(NOTIFICATION)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", is(true)))
            .andExpect(jsonPath("$.message", is("Notification updated!")));
    verify(notificationService, times(1)).updateNotification(anyLong(), any());
  }

  @Test
  public void deleteNotif() throws Exception {
    when(notificationService.deleteNotification(anyLong()))
            .thenReturn(BaseResponse.success("Notification deleted!"));
    mvc.perform(delete(NOTIF_API + "/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", is(true)))
            .andExpect(jsonPath("$.message", is("Notification deleted!")));
    verify(notificationService, times(1)).deleteNotification(anyLong());
  }
}
