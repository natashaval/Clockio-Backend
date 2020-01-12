package com.future.clockio.client.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PushNotificationRequest {
  private String title;
  private String message;
  private String topic;
  private String token;

  public PushNotificationRequest(String title, String message, String topic) {
    this.title = title;
    this.message = message;
    this.topic = topic;
  }
}
