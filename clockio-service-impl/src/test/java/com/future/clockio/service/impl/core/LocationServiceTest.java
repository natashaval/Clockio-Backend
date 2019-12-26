package com.future.clockio.service.impl.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.clockio.entity.core.Location;
import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.repository.core.LocationRepository;
import com.future.clockio.request.core.LocationHistoryRequest;
import com.future.clockio.request.core.LocationRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.company.EmployeeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static com.future.clockio.service.impl.helper.EntityMock.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LocationServiceTest {
  @Mock
  private EmployeeService employeeService;

  @Mock
  private LocationRepository locationRepository;

  @Mock
  private ObjectMapper mapper;

  @InjectMocks
  private LocationServiceImpl locationService;

  private static final UUID LOCATION_ID = UUID.randomUUID();
  private static final Location LOCATION = Location.builder()
          .id(LOCATION_ID)
          .employee(EMPLOYEE)
          .latitude(DOUBLE_RANDOM)
          .longitude(DOUBLE_RANDOM)
          .build();
  private Optional<Location> locationOpt;

  @Before
  public void setUp() {
    locationOpt = Optional.of(LOCATION);
  }

  @Test
  public void findById_Found() {
    when(locationRepository.findById(any(UUID.class))).thenReturn(locationOpt);
    Location res = locationService.findById(LOCATION_ID);
    Assert.assertEquals(LOCATION_ID, res.getId());
    Assert.assertEquals(DOUBLE_RANDOM, res.getLatitude(), 0.01);
    Assert.assertEquals(DOUBLE_RANDOM, res.getLongitude(), 0.01);
  }

  @Test
  public void findById_NotFound() {
    try {
      employeeService.findById(LOCATION_ID);
    } catch (DataNotFoundException e) {
      Assert.assertEquals("Location not found!", e.getMessage());
    }
  }

  @Test
  public void addLocation() {
    LocationRequest request = new LocationRequest(EMP_ID, DOUBLE_RANDOM, DOUBLE_RANDOM);
    when(employeeService.findById(any(UUID.class))).thenReturn(EMPLOYEE);
    doReturn(LOCATION).when(mapper).convertValue(any(), eq(Location.class));
    BaseResponse res = locationService.addLocation(request);
    Assert.assertTrue(res.isSuccess());
    Assert.assertEquals("Location saved!", res.getMessage());
  }

  @Test
  public void findEmployeeLocation() {
    LocationHistoryRequest request = new LocationHistoryRequest(EMP_ID, 2019, 12, 12);
    List<Location> locationList = Collections.singletonList(LOCATION);
    when(locationRepository.findAllByEmployee_IdAndCreatedAtBetween(any(UUID.class),
            any(Date.class), any(Date.class))).thenReturn(locationList);
    List<Location> res = locationService.findEmployeeLocation(request);
    Assert.assertEquals(locationList, res);
    Assert.assertEquals(locationList.size(), res.size());
  }
}
