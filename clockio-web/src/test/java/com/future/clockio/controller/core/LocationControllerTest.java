package com.future.clockio.controller.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.clockio.entity.core.Location;
import com.future.clockio.request.core.LocationRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.core.LocationService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static com.future.clockio.controller.helper.EntityMock.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class LocationControllerTest {
  private MockMvc mvc;

  @Spy private ObjectMapper mapper;
  @Mock private LocationService locationService;
  @InjectMocks private LocationController controller;

  private static final String LOCATION_API = "/api/location";
  private static final UUID LOCATION_ID = UUID.randomUUID();
  private static final Date DATE_NOW = new Date();
  private static final Location LOCATION = Location.builder()
          .id(LOCATION_ID)
          .employee(EMPLOYEE)
          .latitude(DOUBLE_RANDOM)
          .longitude(DOUBLE_RANDOM)
          .build();
  private String dateString;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.standaloneSetup(controller).build();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    dateString = sdf.format(DATE_NOW);
  }

  @Test
  public void findById() throws Exception {
    when(locationService.findById(any(UUID.class))).thenReturn(LOCATION);
    mvc.perform(get(LOCATION_API + "/{id}", 1)
    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.latitude", is(DOUBLE_RANDOM)))
            .andExpect(jsonPath("$.longitude", is(DOUBLE_RANDOM)));
  }

  @Test
  public void getLocationHistory() throws Exception {
    when(locationService.findEmployeeLocation(any(UUID.class), any(Date.class), any(Date.class)))
            .thenReturn(Collections.singletonList(LOCATION));
    mvc.perform(get(LOCATION_API + "/{empId}/history", EMP_ID)
    .param("start", dateString)
    .param("end", dateString)
    ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    verify(locationService, times(1)).findEmployeeLocation(any(UUID.class), any(Date.class),
            any(Date.class));
  }

  @Test
  public void addLocation() throws Exception {
    LocationRequest request = new LocationRequest(EMP_ID, DOUBLE_RANDOM, DOUBLE_RANDOM);
    when(locationService.addLocation(any(LocationRequest.class))).thenReturn(BaseResponse.success("Location saved!"));
    mvc.perform(post(LOCATION_API)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request))
    ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", is(true)))
            .andExpect(jsonPath("$.message", equalTo("Location saved!")));
    verify(locationService, times(1)).addLocation(any(LocationRequest.class));
  }
}
