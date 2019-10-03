package com.future.clockio.repository.core;

import com.future.clockio.entity.core.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
}
