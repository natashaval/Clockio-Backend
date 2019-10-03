package com.future.clockio.controller.company;

import com.future.clockio.command.ImageDestroyCommand;
import com.future.clockio.command.ImageUploadCommand;
import com.future.clockio.request.company.ImageDestroyRequest;
import com.future.clockio.request.company.ImageUploadRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.response.company.ImageDestroyResponse;
import com.future.clockio.response.company.ImageUploadResponse;
import com.future.clockio.service.company.ImageService;
import com.future.clockio.service.core.CommandExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/employees/image")
public class ImageController {
  @Autowired
  private ImageService imageService;

  @PostMapping(value = "/{id}/upload")
  private BaseResponse uploadImage(
          @PathVariable("id") String id,
          @RequestParam("file") MultipartFile file) {
    ImageUploadRequest request = new ImageUploadRequest();
    request.setEmployeeId(id);
    request.setFile(file);
    return imageService.uploadImage(request);
  }

  @DeleteMapping("/{id}/destroy")
  private BaseResponse destroyImage(
          @PathVariable("id") String id,
          @RequestBody ImageDestroyRequest request) {
    request.setEmployeeId(id);
    return imageService.destroyImage(request);
  }
}
