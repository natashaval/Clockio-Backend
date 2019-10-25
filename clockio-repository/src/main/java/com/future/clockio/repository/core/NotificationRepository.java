package com.future.clockio.repository.core;

import com.future.clockio.entity.core.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, String> {
}
