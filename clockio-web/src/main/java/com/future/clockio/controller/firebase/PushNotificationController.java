package com.future.clockio.controller.firebase;

import com.future.clockio.client.firebase.PushNotificationService;
import com.future.clockio.client.firebase.PushNotificationServiceImpl;
import com.future.clockio.client.model.request.PushNotificationRequest;
import com.future.clockio.client.model.response.PushNotificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/firebase")
public class PushNotificationController {

  @Autowired
  private PushNotificationService pushNotificationService;

  @PostMapping("/notification/topic")
  public ResponseEntity sendNotification(@RequestBody PushNotificationRequest request) {
    pushNotificationService.sendPushNotificationWithoutData(request);
    return new ResponseEntity(new PushNotificationResponse(HttpStatus.OK.value(), "Notification " +
            "without data has been sent"), HttpStatus.OK);
  }

  @PostMapping("/notification/token")
  public ResponseEntity sendTokenNotification(@RequestBody PushNotificationRequest request) {
    pushNotificationService.sendPushNotificationToToken(request);
    return new ResponseEntity(new PushNotificationResponse(HttpStatus.OK.value(), "Notification " +
            "to token has been sent"), HttpStatus.OK);
  }

  @PostMapping("/notification/data")
  public ResponseEntity sendDataNotification(@RequestBody PushNotificationRequest request) {
    pushNotificationService.sendPushNotification(request);
    return new ResponseEntity(new PushNotificationResponse(HttpStatus.OK.value(), "Notification " +
            "with data has been sent"), HttpStatus.OK);
  }

  @GetMapping("/notification")
  public ResponseEntity sendSampleNotification() {
    pushNotificationService.sendSamplePushNotification();
    return new ResponseEntity(new PushNotificationResponse(HttpStatus.OK.value(), "Notification " +
            "SAMPLE has been sent"), HttpStatus.OK);
  }
}
