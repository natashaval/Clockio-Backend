package com.future.clockio.client.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

//https://blog.mestwin.net/send-push-notifications-from-spring-boot-server-side-application-using-fcm/
//https://golb.hplar.ch/2017/02/Send-messages-from-Spring-Boot-to-Ionic-2-over-FCM.html
@Service
@Slf4j
public class FCMInitializer {
  @Value("${fcm.firebase-config-file}")
  private String firebaseConfigPath;

  @PostConstruct
  public void initialize() {
    try {
      FirebaseOptions options = FirebaseOptions.builder()
              .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream()))
              .build();
      if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options);
        log.info("Firebase application has been initialized!");
      }
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }
}
