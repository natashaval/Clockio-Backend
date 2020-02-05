package com.future.clockio.service.impl.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.clockio.command.ImageDestroyCommand;
import com.future.clockio.command.ImageUploadCommand;
import com.future.clockio.entity.company.Employee;
import com.future.clockio.entity.company.Photo;
import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.repository.company.EmployeeRepository;
import com.future.clockio.repository.company.PhotoRepository;
import com.future.clockio.request.company.ImageDestroyRequest;
import com.future.clockio.request.company.ImageSaveRequest;
import com.future.clockio.request.company.ImageUploadRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.response.company.ImageDestroyResponse;
import com.future.clockio.response.company.ImageUploadResponse;
import com.future.clockio.service.company.EmployeeService;
import com.future.clockio.service.company.ImageService;
import com.future.clockio.service.core.CommandExecutorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

  private CommandExecutorService commandExecutor;
  private PhotoRepository photoRepository;
  private EmployeeService employeeService;

  @Autowired
  public ImageServiceImpl(CommandExecutorService commandExecutor,
                          EmployeeService employeeService,
                          PhotoRepository photoRepository) {
    this.commandExecutor = commandExecutor;
    this.photoRepository = photoRepository;
    this.employeeService = employeeService;
  }

  @Override
  public BaseResponse uploadImage(ImageUploadRequest request) {
    Employee employee = employeeService.findById(request.getEmployeeId());

    request.setFaceListId(employee.getFaceListId());
    ImageUploadResponse imageResponse =
            commandExecutor.executeCommand(ImageUploadCommand.class, request);
    log.info("Image Upload Response" + imageResponse);
    Photo photo = new Photo();
    photo.setUrl(imageResponse.getUrl());
    photo.setPublicId(imageResponse.getPublicId());
    photo.setEmployee(employee);
    photo.setMainPhoto(request.isPersisted());
    photoRepository.save(photo);

    BaseResponse response = BaseResponse.success("Employee Image uploaded!");
    response.setData(imageResponse);
    return response;
  }

  @Override
  public BaseResponse destroyImage(ImageDestroyRequest request) {
    log.info("Image Destroy request" + request);
    ImageDestroyResponse response = commandExecutor.executeCommand(ImageDestroyCommand.class, request);
//    response.getDeleted().forEach((k,v) -> {
//      if (v.equals("deleted")) {
//        if (employee.getPhotoUrl().contains(k)) employee.getPhotoUrl().remove(k);
//      }
//    });
//    employeeRepository.save(employee);
    for (String publicId: request.getPublicId()) {
      photoRepository.deleteById(publicId);
    }

    return BaseResponse.success("Employee Image deleted!");
  }

  @Override
  public BaseResponse saveImage(ImageSaveRequest request) {
    Employee employee = employeeService.findById(request.getEmployeeId());
    Photo photo = new Photo();
    photo.setUrl(request.getUrl());
    photo.setEmployee(employee);
    photo.setPublicId(request.getPublicId());
    photo.setMainPhoto(false);
    photoRepository.save(photo);
    return BaseResponse.success("Photo saved!");
  }
}
