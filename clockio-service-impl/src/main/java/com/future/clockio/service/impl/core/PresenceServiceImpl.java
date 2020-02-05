package com.future.clockio.service.impl.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.clockio.entity.company.Employee;
import com.future.clockio.entity.company.Photo;
import com.future.clockio.entity.core.Presence;
import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.exception.InvalidRequestException;
import com.future.clockio.repository.company.EmployeeRepository;
import com.future.clockio.repository.company.PhotoRepository;
import com.future.clockio.repository.core.PresenceRepository;
import com.future.clockio.request.core.PresenceRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.core.FaceService;
import com.future.clockio.service.core.PresenceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class PresenceServiceImpl implements PresenceService {

  @Autowired
  private ObjectMapper mapper;

  private EmployeeRepository employeeRepository;
  private PhotoRepository photoRepository;
  private PresenceRepository presenceRepository;
  private FaceService faceService;

  @Autowired
  public PresenceServiceImpl(EmployeeRepository employeeRepository,
                             PhotoRepository photoRepository,
                             PresenceRepository presenceRepository,
                             FaceService faceService) {
    this.employeeRepository = employeeRepository;
    this.photoRepository = photoRepository;
    this.presenceRepository = presenceRepository;
    this.faceService = faceService;
  }

  @Override
  public List<Presence> findAll() {
    return presenceRepository.findAll();
  }

  @Override
  public Presence findById(UUID id) {
    return presenceRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Presence not found!"));
  }

  @Override
  public BaseResponse checkIn(PresenceRequest request) {
    Employee employee = employeeRepository.findById(request.getEmployeeId())
            .orElseThrow(() -> new DataNotFoundException("Employee not found!"));

    boolean photoExists = !TextUtils.isEmpty(request.getUrl());
    Photo photo = new Photo();
    if (photoExists) {
//      String faceId = faceService.faceDetect(request.getUrl());
//      boolean isSimilar = faceService.checkSimilarity(faceId, employee.getFaceListId());
//      if (!isSimilar) throw new InvalidRequestException("Face Not Match!");

      photo = photoRepository.findByUrl(request.getUrl());
    }

    Presence presence = mapper.convertValue(request, Presence.class);
    log.debug("Presence" + presence);

    // log presence
    presence.setEmployee(employee);
    if (photoExists) presence.setPhoto(photo);
    presence.setCheckIn(new Date()); // get current timestamp
    Presence presenceResult = Optional.of(presence)
            .map(presenceRepository::save)
            .orElseThrow(() -> new InvalidRequestException("Fail check in!"));
    // save employee last Presence checkIn
    employee.setLastCheckIn(presenceResult.getCheckIn());
    employee.setLastLatitude(presenceResult.getLatitude());
    employee.setLastLongitude(presenceResult.getLongitude());
    employeeRepository.save(employee);

    BaseResponse response = BaseResponse.success("Success check in!");
    response.setData(presenceResult);
    return response;
  }

  @Override
  public BaseResponse checkOut(PresenceRequest request) {
    Presence presence = findById(request.getPresenceId());
    presence.setCheckOut(new Date()); // get current timestamp
    Presence presenceResult = presenceRepository.save(presence);

    // save employee last Presence checkOut
    Employee employee = presenceResult.getEmployee();
    employee.setLastCheckOut(presenceResult.getCheckOut());
    employee.setLastLatitude(request.getLatitude());
    employee.setLastLongitude(request.getLongitude());
    employeeRepository.save(employee);

    return BaseResponse.success("Success check out!");
  }
}
