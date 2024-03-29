package com.future.clockio.controller.company;

import com.future.clockio.command.ImageDestroyCommand;
import com.future.clockio.command.ImageUploadCommand;
import com.future.clockio.request.company.ImageDestroyRequest;
import com.future.clockio.request.company.ImageSaveRequest;
import com.future.clockio.request.company.ImageUploadRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.response.company.ImageDestroyResponse;
import com.future.clockio.response.company.ImageUploadResponse;
import com.future.clockio.service.company.ImageService;
import com.future.clockio.service.core.CommandExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/employees/image")
public class ImageController {
  @Autowired
  private ImageService imageService;

  @PostMapping(value = "/{id}/upload")
  private BaseResponse uploadImage(
          @PathVariable("id") UUID id,
          @RequestParam("photo") MultipartFile photo) {
    ImageUploadRequest request = new ImageUploadRequest();
    request.setEmployeeId(id);
    request.setFile(photo);
    request.setPersisted(true);
    return imageService.uploadImage(request);
  }

  @DeleteMapping("/{id}/destroy")
  private BaseResponse destroyImage(
          @PathVariable("id") UUID id,
          @RequestBody ImageDestroyRequest request) {
    request.setEmployeeId(id);
    return imageService.destroyImage(request);
  }

  @PostMapping("/{id}/save")
  private BaseResponse saveImage(@PathVariable("id") UUID id,
                                 @RequestBody ImageSaveRequest request) {
    return imageService.saveImage(request);
  }
}
