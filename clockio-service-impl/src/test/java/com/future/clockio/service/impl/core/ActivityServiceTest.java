package com.future.clockio.service.impl.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.clockio.entity.company.Employee;
import com.future.clockio.entity.core.Activity;
import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.repository.core.ActivityRepository;
import com.future.clockio.request.core.ActivityRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.company.EmployeeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static com.future.clockio.service.impl.helper.EntityMock.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ActivityServiceTest {
  @Mock
  private ActivityRepository activityRepository;

  @Mock
  private EmployeeService employeeService;

  @Mock
  private ObjectMapper mapper;

  @InjectMocks
  private ActivityServiceImpl activityService;

  private static final UUID ACT_ID = UUID.randomUUID();
  private static final String ACT_TITLE = "Activity";
  private static final String ACT_CONTENT = "Activity Content";
  private static final Activity ACTIVITY = Activity.builder()
          .id(ACT_ID)
          .title(ACT_TITLE)
          .content(ACT_CONTENT)
          .employee(EMPLOYEE).build();

  private static final ActivityRequest ACTIVITY_REQUEST = ActivityRequest.builder()
          .employeeId(EMP_ID)
          .title(ACT_TITLE)
          .content(ACT_CONTENT)
          .date(DATE_NOW)
          .build();

  private Optional<Activity> actOpt;
  private List<Activity> actList;
  private Page<Activity> actPage;

  @Before
  public void setUp() {
    actOpt = Optional.of(ACTIVITY);
    actList = Collections.singletonList(ACTIVITY);
    actPage = new PageImpl<>(actList);
  }

  private void mockFindById() {
    when(activityRepository.findById(any(UUID.class))).thenReturn(actOpt);
  }

  private void mockEmployee() {
    when(employeeService.findById(any(UUID.class))).thenReturn(EMPLOYEE);
  }

  private void assertActivityNotFound(String message) {
    Assert.assertEquals("Activity not found!", message);
  }

  @Test
  public void findById_Found() {
    mockFindById();
    Activity res = activityService.findById(ACT_ID);
    Assert.assertEquals(ACT_ID, res.getId());
    Assert.assertEquals(ACT_TITLE, res.getTitle());
    Assert.assertEquals(ACT_CONTENT, res.getContent());
  }

  @Test
  public void findById_NotFound() {
    try {
      activityService.findById(ACT_ID);
    } catch (DataNotFoundException e) {
      assertActivityNotFound(e.getMessage());
    }
  }

  @Test
  public void deleteById_Success() {
    when(activityRepository.existsById(any(UUID.class))).thenReturn(true);
    BaseResponse res = activityService.deleteActivity(ACT_ID);
    Assert.assertTrue(res.isSuccess());
    Assert.assertEquals("Activity deleted!", res.getMessage());
  }

  @Test
  public void deleteById_Failed() {
    try {
      activityRepository.deleteById(ACT_ID);
    } catch (DataNotFoundException e) {
      assertActivityNotFound(e.getMessage());
    }
  }

  @Test
  public void createActivity() {
    ActivityRequest request = ActivityRequest.builder()
                    .title(ACT_TITLE).content(ACT_CONTENT).employeeId(EMP_ID).build();
    mockEmployee();
    doReturn(ACTIVITY).when(mapper).convertValue(any(), eq(Activity.class));
    BaseResponse res = activityService.createActivity(request);
    Assert.assertTrue(res.isSuccess());
    Assert.assertEquals("Activity saved!", res.getMessage());
  }

  @Test
  public void findAll() {
    when(activityRepository.findAll(ArgumentMatchers.isA(Pageable.class))).thenReturn(actPage);
    Page<Activity> result = activityService.findAll(PAGE_START, PAGE_SIZE);
    Assert.assertEquals(actList.size(), result.getTotalElements());
    Assert.assertEquals(actList, result.getContent());
  }

  @Test
  public void findAllPageable() {
    when(activityRepository.findAllByEmployee_IdOrderByDateDesc(any(UUID.class),
            any(Pageable.class))).thenReturn(actPage);
    Page<Activity> result = activityService.findAllPageable(EMP_ID, PAGE_START, PAGE_SIZE);
    Assert.assertEquals(actList.size(), result.getTotalElements());
    Assert.assertEquals(actList, result.getContent());
  }

  @Test
  public void findByEmployeeAndDateToday() {
    when(activityRepository.findActivityTodayByEmployee(any(UUID.class), any(Date.class))).thenReturn(actList);
    List<Activity> result = activityService.findByEmployeeAndDateToday(EMP_ID, DATE_NOW);
    Assert.assertEquals(result.size(), actList.size());
    Assert.assertEquals(result, actList);
  }

  @Test
  public void findByEmployeeAndDateBetween() {
    when(activityRepository.findAllByEmployee_IdAndDateBetween(any(UUID.class), any(Date.class),
            any(Date.class), any(Pageable.class))).thenReturn(actPage);
    Page<Activity> result = activityService.findByEmployeeAndDateBetween(EMP_ID, DATE_NOW,
            DATE_NOW, PAGE_START, PAGE_SIZE);
    Assert.assertEquals(actList.size(), result.getTotalElements());
    Assert.assertEquals(actList, result.getContent());
  }

  @Test
  public void updateActivity_NotFound() {
    try {
      activityService.updateActivity(ACT_ID, ACTIVITY_REQUEST);
    } catch (DataNotFoundException e) {
      assertActivityNotFound(e.getMessage());
    }
  }

  @Test
  public void updateActivity_Success() {
    mockFindById();
    BaseResponse response = activityService.updateActivity(ACT_ID, ACTIVITY_REQUEST);
    Assert.assertTrue(response.isSuccess());
    Assert.assertEquals("Activity updated!", response.getMessage());
  }
}
