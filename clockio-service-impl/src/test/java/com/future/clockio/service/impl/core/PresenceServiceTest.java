package com.future.clockio.service.impl.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.clockio.entity.company.Employee;
import com.future.clockio.entity.company.Photo;
import com.future.clockio.entity.core.Presence;
import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.repository.company.EmployeeRepository;
import com.future.clockio.repository.company.PhotoRepository;
import com.future.clockio.repository.core.PresenceRepository;
import com.future.clockio.request.core.PresenceRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.core.FaceService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.future.clockio.service.impl.helper.EntityMock.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PresenceServiceTest {
  @Mock
  private ObjectMapper mapper;
  @Mock
  private EmployeeRepository employeeRepository;
  @Mock
  private PhotoRepository photoRepository;
  @Mock
  private PresenceRepository presenceRepository;
  @Mock
  private FaceService faceService;
  @InjectMocks
  private PresenceServiceImpl presenceService;

  private static final UUID presenceId = UUID.randomUUID();
  private static final Photo PHOTO = new Photo();
  private static final Presence PRESENCE = Presence.builder()
          .id(presenceId)
          .employee(EMPLOYEE)
          .checkIn(DATE_NOW)
          .checkOut(DATE_NOW)
          .photo(PHOTO)
          .latitude(0.0)
          .longitude(0.0)
          .build();

  private static final PresenceRequest PRESENCE_REQUEST = PresenceRequest.builder()
          .employeeId(EMP_ID)
          .presenceId(presenceId)
          .url("http://picsum.photos")
          .checkIn(DATE_NOW)
          .checkOut(DATE_NOW)
          .latitude(0.0)
          .longitude(0.0)
          .build();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  private void assertPresenceNotFound(String message) {
    Assert.assertEquals("Presence not found!", message);
  }

  @Test
  public void findAll() {
    List<Presence> presences = Collections.singletonList(PRESENCE);
    when(presenceRepository.findAll()).thenReturn(presences);
    List<Presence> result = presenceService.findAll();
    Assert.assertEquals(presences.size(), result.size());
    Assert.assertEquals(presences, result);
  }

  @Test
  public void findById_Success() {
    when(presenceRepository.findById(any(UUID.class))).thenReturn(Optional.of(PRESENCE));
    Presence result = presenceService.findById(presenceId);
    Assert.assertEquals(PRESENCE, result);
  }

  @Test
  public void findById_NotFound() {
    try {
      presenceService.findById(presenceId);
    } catch (DataNotFoundException e) {
      assertPresenceNotFound(e.getMessage());
    }
  }

  @Test
  public void checkOut_PresenceNotFound() {
    try {
      presenceService.checkOut(PRESENCE_REQUEST);
    } catch (DataNotFoundException e) {
      assertPresenceNotFound(e.getMessage());
    }
  }

  @Test
  public void checkOut_Success() {
    when(presenceRepository.findById(any(UUID.class))).thenReturn(Optional.of(PRESENCE));
    when(presenceRepository.save(any(Presence.class))).thenReturn(PRESENCE);
    BaseResponse response = presenceService.checkOut(PRESENCE_REQUEST);
    Assert.assertTrue(response.isSuccess());
    Assert.assertEquals("Success check out!", response.getMessage());
    verify(presenceRepository, times(1)).save(any(Presence.class));
    verify(employeeRepository, times(1)).save(any(Employee.class));
  }

  @Test
  public void checkIn_EmployeeNotFound() {
    try {
      presenceService.checkIn(PRESENCE_REQUEST);
    } catch (DataNotFoundException e) {
      Assert.assertEquals("Employee not found!", e.getMessage());
    }
  }

  @Test
  public void checkIn_Success() {
    when(employeeRepository.findById(any(UUID.class))).thenReturn(Optional.of(EMPLOYEE));
    when(photoRepository.findByUrl(anyString())).thenReturn(PHOTO);
    when(presenceRepository.save(any(Presence.class))).thenReturn(PRESENCE);
    doReturn(PRESENCE).when(mapper).convertValue(any(), eq(Presence.class));
    BaseResponse response = presenceService.checkIn(PRESENCE_REQUEST);
    Assert.assertTrue(response.isSuccess());
    Assert.assertEquals("Success check in!", response.getMessage());
    verify(presenceRepository, times(1)).save(any(Presence.class));
    verify(employeeRepository, times(1)).save(any(Employee.class));
  }
}
