package com.future.clockio.client.firebase;

import com.future.clockio.client.model.request.PushNotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class PushNotificationService {

  @Value("#{${fcm.notification-default}}")
  private Map<String, String> defaults;


  private FCMService fcmService;

  public PushNotificationService(FCMService fcmService) {
    this.fcmService = fcmService;
  }

  public void sendSamplePushNotification() {
    try {
      fcmService.sendMessageWithoutData(getSamplePushNotificationRequest());
    } catch (InterruptedException | ExecutionException e) {
      log.error(e.getMessage());
    }
  }

  public void sendPushNotification(PushNotificationRequest request) {
    try {
      fcmService.sendMessage(getSamplePayloadData(), request);
    } catch (InterruptedException | ExecutionException e) {
      log.error(e.getMessage());
    }
  }

  public void sendPushNotificationWithoutData(PushNotificationRequest request) {
    try {
      fcmService.sendMessageWithoutData(request);
    } catch (InterruptedException | ExecutionException e) {
      log.error(e.getMessage());
    }
  }

  public void sendPushNotificationToToken(PushNotificationRequest request) {
    try {
      fcmService.sendMessageToToken(request);
    } catch (InterruptedException | ExecutionException e) {
      log.error(e.getMessage());
    }
  }

  private Map<String, String> getSamplePayloadData() {
    Map<String, String> pushData = new HashMap<>();
    pushData.put("messageId", defaults.get("payloadMessageId"));
    pushData.put("text", defaults.get("payloadData") + " " + LocalDateTime.now());
    return pushData;
  }

  private PushNotificationRequest getSamplePushNotificationRequest() {
    PushNotificationRequest request = new PushNotificationRequest(
            defaults.get("title"),
            defaults.get("message"),
            defaults.get("topic"));
    return request;
  }
}
