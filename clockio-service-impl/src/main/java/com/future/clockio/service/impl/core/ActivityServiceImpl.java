package com.future.clockio.service.impl.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.clockio.entity.company.Employee;
import com.future.clockio.entity.core.Activity;
import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.repository.company.EmployeeRepository;
import com.future.clockio.repository.core.ActivityRepository;
import com.future.clockio.request.core.ActivityRequest;
import com.future.clockio.request.core.StatusRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.company.EmployeeService;
import com.future.clockio.service.core.ActivityService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ActivityServiceImpl implements ActivityService {

  private ObjectMapper mapper;
  private ActivityRepository activityRepository;
  private EmployeeService employeeService;

  @Autowired
  public ActivityServiceImpl(ObjectMapper mapper, ActivityRepository activityRepository,
                             EmployeeService employeeService) {
    this.mapper = mapper;
    this.activityRepository = activityRepository;
    this.employeeService = employeeService;
  }

  @Override
  public BaseResponse createActivity(ActivityRequest request) {
    Employee employee = employeeService.findById(request.getEmployeeId());
    Activity activity = mapper.convertValue(request, Activity.class);
    activity.setEmployee(employee);
    activityRepository.save(activity);
    return BaseResponse.success("Activity saved!");
  }

  @Override
  public BaseResponse updateActivity(UUID id, ActivityRequest request) {
    Activity activity = findById(id);
    activity = copyProperties(request, activity);
    activityRepository.save(activity);
    return BaseResponse.success("Activity updated!");
  }

  @Override
  public Activity findById(UUID id) {
    Activity activity = activityRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Activity not found!"));
    return activity;
  }

  @Override
  public Page<Activity> findAllPageable(UUID employeeId, int page, int size) {
    return activityRepository.findAllByEmployee_IdOrderByDateDesc(employeeId,
            PageRequest.of(page, size));
  }

  @Override
  public List<Activity> findByEmployeeAndDateToday(UUID employeeId, Date date) {
    return activityRepository.findActivityTodayByEmployee(employeeId, date);
  }

  @Override
  public Page<Activity> findByEmployeeAndDateBetween(UUID employeeId, Date start, Date end, int page, int size) {
    DateTime startDateTime = new DateTime(start).withTime(0,0,0,0);
    DateTime endDateTime = new DateTime(end).withTime(23,59,59,999);
    return activityRepository.findAllByEmployee_IdAndDateBetween(employeeId,
            startDateTime.toDate(), endDateTime.toDate(), PageRequest.of(page, size));
  }

  @Override
  public Page<Activity> findAll(int page, int size) {
    return activityRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC,
            "date")));
  }

  @Override
  public BaseResponse deleteActivity(UUID id) {
    Boolean exist = activityRepository.existsById(id);
    if (exist) {
      activityRepository.deleteById(id);
      return BaseResponse.success("Activity deleted!");
    } else {
      throw new DataNotFoundException("Activity not found!");
    }
  }

  private Activity copyProperties(ActivityRequest source, Activity target) {
    target.setTitle(source.getTitle());
    target.setContent(source.getContent());
    target.setStartTime(source.getStartTime());
    target.setEndTime(source.getEndTime());
    return target;
  }
}
