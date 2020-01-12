package com.future.clockio.client.firebase;

import com.future.clockio.client.model.request.PushNotificationRequest;
import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class FCMService {

  public void sendMessage(Map<String, String> data, PushNotificationRequest request)
  throws InterruptedException, ExecutionException {
    Message message = getPreconfiguredMessageWithData(data, request);
    String response = sendAndGetResponse(message);
    log.info("Sent message with data. Topic: " + request.getTopic() + ",response: " + response);
  }

  public void sendMessageWithoutData(PushNotificationRequest request)
          throws InterruptedException, ExecutionException {
    Message message = getPreconfiguredMessageWithoutData(request);
    String response = sendAndGetResponse(message);
    log.info("Sent message without data. Topic: " + request.getTopic() + ",response: " + response);
  }

  public void sendMessageToToken(PushNotificationRequest request)
          throws InterruptedException, ExecutionException {
    Message message = getPreconfiguredMessageToToken(request);
    String response = sendAndGetResponse(message);
    log.info("Sent message to token. Device Token: " + request.getTopic() + ",response: " + response);
  }

  private String sendAndGetResponse(Message message) throws ExecutionException, InterruptedException {
    return FirebaseMessaging.getInstance().sendAsync(message).get();
  }

  private AndroidConfig getAndroidConfig(String topic) {
    return AndroidConfig.builder()
            .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
            .setPriority(AndroidConfig.Priority.HIGH)
            .setNotification(AndroidNotification.builder().setSound("default").setColor("#FFFF00").setTag(topic).build())
            .build();
  }

  private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {
    AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
    return Message.builder()
            .setAndroidConfig(androidConfig).setNotification(
                    Notification.builder().setTitle(request.getTitle()).setBody(request.getMessage()).build()
            );
  }

  private Message getPreconfiguredMessageToToken(PushNotificationRequest request) {
    return getPreconfiguredMessageBuilder(request).setToken(request.getToken())
            .build();
  }

  private Message getPreconfiguredMessageWithoutData(PushNotificationRequest request) {
    return getPreconfiguredMessageBuilder(request).setTopic(request.getTopic())
            .build();
  }

  private Message getPreconfiguredMessageWithData(Map<String, String> data,
                                                  PushNotificationRequest request) {
    return getPreconfiguredMessageBuilder(request).putAllData(data).setTopic(request.getTopic())
            .build();
  }

}
