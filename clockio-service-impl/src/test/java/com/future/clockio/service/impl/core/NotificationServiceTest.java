package com.future.clockio.service.impl.core;

import com.future.clockio.entity.core.Notification;
import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.repository.core.NotificationRepository;
import com.future.clockio.response.base.BaseResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceTest {
  @Mock
  private NotificationRepository notificationRepository;

  @InjectMocks
  private NotificationServiceImpl notificationService;

  private static final String TITLE = "Title";
  private static final String CONTENT = "Content";
  private static final Notification NOTIFICATION = Notification.builder()
          .id(1L)
          .title(TITLE)
          .content(CONTENT)
          .build();

  private Optional<Notification> notifOpt;

  @Before
  public void setUp() {
    notifOpt = Optional.of(NOTIFICATION);
  }

  @Test
  public void findById_NotFound() {
    try {
      notificationService.findById(1L);
    } catch (DataNotFoundException e) {
      Assert.assertEquals("Notification not found!", e.getMessage());
    }
  }

  @Test
  public void findById_Found() {
    when(notificationRepository.findById(anyLong())).thenReturn(notifOpt);
    Notification notif = notificationService.findById(1L);
    Assert.assertEquals(TITLE, notif.getTitle());
    Assert.assertEquals(CONTENT, notif.getContent());
  }

  @Test
  public void findAll() {
    List<Notification> notifList = Collections.singletonList(NOTIFICATION);
    Page<Notification> notifPage = new PageImpl<>(notifList);
    when(notificationRepository.findAll(ArgumentMatchers.isA(Pageable.class))).thenReturn(notifPage);
    Page<Notification> result = notificationService.findAllPageable(0, 5);
    Assert.assertEquals(notifList.size(), result.getTotalElements());
  }

  @Test
  public void createNotif_NotFound() {
    try {
      notificationService.createNotification(NOTIFICATION);
    } catch (DataNotFoundException e) {
      Assert.assertEquals("Notification not found!", e.getMessage());
    }
  }

  @Test
  public void createNotif_success() {
    when(notificationRepository.save(any(Notification.class))).thenReturn(NOTIFICATION);
    BaseResponse res = notificationService.createNotification(NOTIFICATION);
    Assert.assertTrue(res.isSuccess());
    Assert.assertEquals("Notification created!", res.getMessage());
  }

  @Test
  public void deleteNotif_NotFound() {
    try {
      notificationService.deleteNotification(1L);
    } catch (DataNotFoundException e) {
      Assert.assertEquals("Notification not found!", e.getMessage());
    }
  }

  @Test
  public void deleteNotif_success() {
    when(notificationRepository.existsById(anyLong())).thenReturn(true);
    BaseResponse res = notificationService.deleteNotification(1L);
    Assert.assertTrue(res.isSuccess());
    Assert.assertEquals("Notification deleted!", res.getMessage());
  }

  @Test
  public void updateNotif_NotFound() {
    try {
      notificationService.updateNotification(1L, NOTIFICATION);
    } catch (DataNotFoundException e) {
      Assert.assertEquals("Notification not found!", e.getMessage());
    }
  }

  @Test
  public void updateNotif_success(){
    when(notificationRepository.findById(anyLong())).thenReturn(notifOpt);
    BaseResponse res = notificationService.updateNotification(1L, NOTIFICATION);
    Assert.assertTrue(res.isSuccess());
    Assert.assertEquals("Notification updated!", res.getMessage());
  }
}
