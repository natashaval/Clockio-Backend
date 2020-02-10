package com.future.clockio.controller.core;

import com.future.clockio.entity.core.Notification;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.core.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/notification")
public class NotificationController {
  @Autowired
  private NotificationService notificationService;

  @GetMapping
  public Page<Notification> findAll(@RequestParam(value = "page",
                                            required = false, defaultValue = "0") int page,
                                    @RequestParam(value = "size",
                                            required = false, defaultValue
                                            = "10") int size) {
    return notificationService.findAllPageable(page, size);
  }

  @GetMapping("/{id}")
  public Notification findById(@PathVariable("id") Long id) {
    return notificationService.findById(id);
  }

  @PostMapping
  public BaseResponse createNotif(@RequestBody Notification notification) {
    return notificationService.createNotification(notification);
  }

  @PutMapping("/{id}")
  public BaseResponse updateNotif(@PathVariable("id") Long id,
                                  @RequestBody Notification notification) {
    return notificationService.updateNotification(id, notification);
  }

  @DeleteMapping("/{id}")
  public BaseResponse deleteById(@PathVariable("id") Long id) {
    return notificationService.deleteNotification(id);
  }
}
