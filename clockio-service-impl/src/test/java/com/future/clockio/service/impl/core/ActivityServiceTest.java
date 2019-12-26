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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static com.future.clockio.service.impl.helper.EntityMock.EMPLOYEE;
import static com.future.clockio.service.impl.helper.EntityMock.EMP_ID;
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

  private Optional<Activity> actOpt;

  @Before
  public void setUp() {
    actOpt = Optional.of(ACTIVITY);
  }

  private void mockFindById() {
    when(activityRepository.findById(any(UUID.class))).thenReturn(actOpt);
  }

  private void mockEmployee() {
    when(employeeService.findById(any(UUID.class))).thenReturn(EMPLOYEE);
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
      Assert.assertEquals("Activity not found!", e.getMessage());
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
  public void createActivity() {
    ActivityRequest request = ActivityRequest.builder()
                    .title(ACT_TITLE).content(ACT_CONTENT).employeeId(EMP_ID).build();
    mockEmployee();
    doReturn(ACTIVITY).when(mapper).convertValue(any(), eq(Activity.class));
    BaseResponse res = activityService.createActivity(request);
    Assert.assertTrue(res.isSuccess());
    Assert.assertEquals("Activity saved!", res.getMessage());
  }

}
