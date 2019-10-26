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
import com.future.clockio.request.company.ImageUploadRequest;
import com.future.clockio.request.core.PresenceRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.response.company.ImageUploadResponse;
import com.future.clockio.service.core.FaceService;
import com.future.clockio.service.core.PresenceService;
import lombok.extern.slf4j.Slf4j;
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
  public BaseResponse checkInPhotoUpload(ImageUploadRequest request) {
    Presence presence = presenceRepository.findById(request.getPresenceId())
            .orElseThrow(() -> new InvalidRequestException("Presence Id not found!"));

    // upload image to Cloudinary
    request.setFaceListId(presence.getEmployee().getFaceListId());
    ImageUploadResponse uploadResponse = faceService.uploadImage(request);

    // save to photo repository
    Photo photo = mapper.convertValue(uploadResponse, Photo.class);
    photo.setPublicId(uploadResponse.getPublicId());
    photo.setEmployee(presence.getEmployee());
    photoRepository.save(photo);

    // save one to one Presence with Photo
    presence.setPhoto(photo);
    presenceRepository.save(presence);

    return BaseResponse.success("Presence photo uploaded!");
  }

  @Override
  public List<Presence> findAll() {
    return presenceRepository.findAll();
  }

  @Override
  public BaseResponse checkIn(PresenceRequest request) {
    Presence presence = mapper.convertValue(request, Presence.class);
    log.debug("Presence" + presence);

    Employee employee = employeeRepository.findById(request.getEmployeeId())
            .orElseThrow(() -> new DataNotFoundException("Employee not found!"));

    // log presence
    presence.setEmployee(employee);
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
    response.getData().put("presenceId", presenceResult.getId().toString());
    return response;
  }

  @Override
  public BaseResponse checkOut(PresenceRequest request) {
    Presence presence = presenceRepository.findById(request.getPresenceId())
            .orElseThrow(() -> new InvalidRequestException("Presence not found!"));
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
