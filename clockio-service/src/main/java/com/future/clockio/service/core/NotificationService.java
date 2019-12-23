package com.future.clockio.service.core;

import com.future.clockio.entity.core.Notification;
import com.future.clockio.response.base.BaseResponse;
import org.springframework.data.domain.Page;

public interface NotificationService {
  Page<Notification> findAllPageable(int page, int size);
  Notification findById(Long id);
  BaseResponse createNotification(Notification notification);
  BaseResponse updateNotification(Long id, Notification notification);
  BaseResponse deleteNotification(Long id);
}
