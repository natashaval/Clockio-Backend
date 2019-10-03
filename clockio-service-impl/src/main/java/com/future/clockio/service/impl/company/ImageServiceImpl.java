package com.future.clockio.service.impl.company;

import com.future.clockio.command.ImageDestroyCommand;
import com.future.clockio.command.ImageUploadCommand;
import com.future.clockio.entity.company.Employee;
import com.future.clockio.entity.company.Photo;
import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.repository.company.EmployeeRepository;
import com.future.clockio.request.company.ImageDestroyRequest;
import com.future.clockio.request.company.ImageUploadRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.response.company.ImageDestroyResponse;
import com.future.clockio.response.company.ImageUploadResponse;
import com.future.clockio.service.company.ImageService;
import com.future.clockio.service.core.CommandExecutorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

  private CommandExecutorService commandExecutor;
  private EmployeeRepository employeeRepository;

  @Autowired
  public ImageServiceImpl(CommandExecutorService commandExecutor,
                          EmployeeRepository employeeRepository) {
    this.commandExecutor = commandExecutor;
    this.employeeRepository = employeeRepository;
  }

  @Override
  public BaseResponse uploadImage(ImageUploadRequest request) {
    Employee employee = employeeRepository.findById(request.getEmployeeId())
            .orElseThrow(() -> new DataNotFoundException("Employee not found!"));

    request.setFaceListId(employee.getFaceListId());
    ImageUploadResponse imageResponse =
            commandExecutor.executeCommand(ImageUploadCommand.class, request);
    log.info("Image Upload Response" + imageResponse);
    Photo photo = new Photo();
    photo.setPublicId(imageResponse.getPublicId());
    photo.setUrl(imageResponse.getUrl());
    if (!employee.getPhotoUrl().contains(photo)) {
      employee.getPhotoUrl().add(photo);
    }
    employeeRepository.save(employee);
    BaseResponse response = new BaseResponse(true, "Employee Image uploaded!");
    response.getDetails().put("url", imageResponse.getUrl());
    return response;
  }

  @Override
  public BaseResponse destroyImage(ImageDestroyRequest request) {
    Employee employee = employeeRepository.findById(request.getEmployeeId())
            .orElseThrow(() -> new DataNotFoundException("Employee not found!"));

    log.info("Image Destroy request" + request);
    ImageDestroyResponse response = commandExecutor.executeCommand(ImageDestroyCommand.class, request);
    response.getDeleted().forEach((k,v) -> {
      if (v.equals("deleted")) {
        if (employee.getPhotoUrl().contains(k)) employee.getPhotoUrl().remove(k);
      }
    });
    employeeRepository.save(employee);
    return BaseResponse.success("Employee Image deleted!");
  }
}
