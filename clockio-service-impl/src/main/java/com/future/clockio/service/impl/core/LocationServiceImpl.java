package com.future.clockio.service.impl.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.clockio.entity.company.Employee;
import com.future.clockio.entity.core.Location;
import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.repository.core.LocationRepository;
import com.future.clockio.request.core.LocationRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.company.EmployeeService;
import com.future.clockio.service.core.LocationService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class LocationServiceImpl implements LocationService {

  private ObjectMapper mapper;
  private LocationRepository locationRepository;
  private EmployeeService employeeService;

  @Autowired
  public LocationServiceImpl(ObjectMapper mapper, LocationRepository locationRepository,
                             EmployeeService employeeService) {
    this.mapper = mapper;
    this.locationRepository = locationRepository;
    this.employeeService = employeeService;
  }

  @Override
  public List<Location> findEmployeeLocation(UUID employeeId, Date start, Date end) {
//    https://www.joda.org/joda-time/apidocs/org/joda/time/DateTime.html#withTimeAtStartOfDay%28%29
    DateTime startDateTime = new DateTime(start).withTime(0,0,0,0);
    DateTime endDateTime = new DateTime(end).withTime(23,59,59,999);
    return locationRepository.findAllByEmployee_IdAndCreatedAtBetween(employeeId,
            startDateTime.toDate(), endDateTime.toDate());
  }

  @Override
  public BaseResponse addLocation(LocationRequest request) {
    Employee employee = employeeService.findById(request.getEmployeeId());

    Location location = mapper.convertValue(request, Location.class);
    location.setEmployee(employee);
    locationRepository.save(location);

    return BaseResponse.success("Location saved!");
  }

  @Override
  public Location findById(UUID id) {
    return locationRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Location not found!"));
  }
}
