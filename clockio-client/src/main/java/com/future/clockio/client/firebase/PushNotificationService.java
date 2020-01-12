package com.future.clockio.client.firebase;

import com.future.clockio.client.model.request.PushNotificationRequest;

public interface PushNotificationService {
  void sendSamplePushNotification();
  void sendPushNotification(PushNotificationRequest request);
  void sendPushNotificationWithoutData(PushNotificationRequest request);
  void sendPushNotificationToToken(PushNotificationRequest request);
}
