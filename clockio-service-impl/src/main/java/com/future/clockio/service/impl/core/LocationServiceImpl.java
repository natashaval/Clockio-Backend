package com.future.clockio.service.impl.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.clockio.entity.company.Employee;
import com.future.clockio.entity.core.Location;
import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.repository.company.EmployeeRepository;
import com.future.clockio.repository.core.LocationRepository;
import com.future.clockio.request.core.LocationHistoryRequest;
import com.future.clockio.request.core.LocationRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.core.LocationService;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class LocationServiceImpl implements LocationService {

  @Autowired
  private ObjectMapper mapper;

  private EmployeeRepository employeeRepository;
  private LocationRepository locationRepository;

  @Autowired
  public LocationServiceImpl(EmployeeRepository employeeRepository, LocationRepository locationRepository) {
    this.employeeRepository = employeeRepository;
    this.locationRepository = locationRepository;
  }

  @Override
  public List<Location> findEmployeeLocation(LocationHistoryRequest request) {
//    https://www.joda.org/joda-time/apidocs/org/joda/time/DateTime.html#withTimeAtStartOfDay%28%29
    DateTime start = new DateTime(request.getYear(), request.getMonth(), request.getDay(), 0, 0);
    start.withTimeAtStartOfDay();
    DateTime end = start.plusHours(23).plusMinutes(59).plusSeconds(59);
    return locationRepository.findAllByEmployee_IdAndCreatedAtBetween(request.getEmployeeId(),
            start.toDate(), end.toDate());
  }

  @Override
  public BaseResponse addLocation(LocationRequest request) {
    Employee employee = employeeRepository.findById(request.getEmployeeId())
            .orElseThrow(() -> new DataNotFoundException("Employee not found!"));

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
