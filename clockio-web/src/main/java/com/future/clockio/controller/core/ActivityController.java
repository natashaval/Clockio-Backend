package com.future.clockio.controller.core;

import com.future.clockio.entity.core.Activity;
import com.future.clockio.request.core.ActivityRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.core.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {
  @Autowired
  private ActivityService activityService;

  @GetMapping
  public Page<Activity> findAll(@RequestParam("page") int page,
                                @RequestParam("size") int size) {
    return activityService.findAll(page, size);
  }

  @GetMapping("/employee/{id}")
  public List<Activity> findAllPageable(@PathVariable("id") UUID employeeId,
                                        @RequestParam("page") int page,
                                        @RequestParam("size") int size) {
    return activityService.findAllPageable(employeeId, page, size).getContent();
  }

  @GetMapping("/employee/{id}/today")
  public List<Activity> findActivityToday(@PathVariable("id") UUID employeeId,
                                          @RequestParam("date") @DateTimeFormat(iso =
                                                  DateTimeFormat.ISO.DATE) Date date) {
    return activityService.findByEmployeeAndDateToday(employeeId, date);
  }

  @GetMapping("/employee/{id}/history")
  public Page<Activity> findActivityHistory(@PathVariable("id") UUID employeeId,
                                            @RequestParam("start") @DateTimeFormat(iso =
                                                    DateTimeFormat.ISO.DATE) Date start,
                                            @RequestParam("end") @DateTimeFormat(iso =
                                                    DateTimeFormat.ISO.DATE) Date end,
                                            @RequestParam("page") int page,
                                            @RequestParam("size") int size
  ) {
    return activityService.findByEmployeeAndDateBetween(employeeId, start, end, page, size);
  }

  @PostMapping("/employee/{id}")
  public BaseResponse createActivity(@PathVariable("id") UUID employeeId,
                                     @RequestBody ActivityRequest request) {
    request.setEmployeeId(employeeId);
    return activityService.createActivity(request);
  }

  @PutMapping("/{id}")
  public BaseResponse updateActivity(@PathVariable("id") UUID id,
                                     @RequestBody ActivityRequest request) {
    return activityService.updateActivity(id, request);
  }

  @DeleteMapping("/{id}")
  public BaseResponse deleteActivity(@PathVariable("id") UUID id) {
    return activityService.deleteActivity(id);
  }
}
