package com.future.clockio.service.impl.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.clockio.client.firebase.PushNotificationService;
import com.future.clockio.client.firebase.PushNotificationServiceImpl;
import com.future.clockio.client.model.request.PushNotificationRequest;
import com.future.clockio.entity.constant.FirebaseTopic;
import com.future.clockio.entity.core.Notification;
import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.repository.core.NotificationRepository;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.core.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
  private NotificationRepository notificationRepository;
  private PushNotificationService pushNotificationService;
  private ObjectMapper mapper;

  @Autowired
  public NotificationServiceImpl(NotificationRepository notificationRepository,
                                 PushNotificationService pushNotificationService,
                                 ObjectMapper objectMapper) {
    this.notificationRepository = notificationRepository;
    this.pushNotificationService = pushNotificationService;
    this.mapper = objectMapper;
  }

  @Override
  public Page<Notification> findAllPageable(int page, int size) {
    return notificationRepository.findAll(PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,
            "updatedAt")));
  }

  @Override
  public Notification findById(Long id) {
    return notificationRepository.findById(id).orElseThrow(() -> new DataNotFoundException(
            "Notification not found!"));
  }

  @Override
  public BaseResponse createNotification(Notification notification) {
    PushNotificationRequest pushNotificationRequest = new PushNotificationRequest(
            notification.getTitle(), notification.getContent(), FirebaseTopic.topic
    );
    notificationRepository.save(notification);
    pushNotificationService.sendPushNotificationWithoutData(pushNotificationRequest);
    return BaseResponse.success("Notification created!");
  }

  @Override
  public BaseResponse updateNotification(Long id, Notification notification) {
    Notification notif = findById(id);
    notif = copyProperties(notification, notif);
    notificationRepository.save(notif);
    return BaseResponse.success("Notification updated!");
  }

  @Override
  public BaseResponse deleteNotification(Long id) {
    Boolean exist = notificationRepository.existsById(id);
    if (exist) {
      notificationRepository.deleteById(id);
      return BaseResponse.success("Notification deleted!");
    } else {
      throw new DataNotFoundException("Notification not found!");
    }
  }

  public Notification copyProperties(Notification source, Notification target) {
    target.setTitle(source.getTitle());
    target.setContent(source.getContent());
    target.setStartDate(source.getStartDate());
    target.setEndDate(source.getEndDate());
    target.setLatitude(source.getLatitude());
    target.setLongitude(source.getLongitude());
    return target;
  }
}
