package com.future.clockio.controller.company;

import com.future.clockio.command.ImageUploadCommand;
import com.future.clockio.request.ImageUploadRequest;
import com.future.clockio.response.company.ImageUploadResponse;
import com.future.clockio.service.core.CommandExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/admin/employee/images")
public class ImageController {
  @Autowired
  private CommandExecutorService commandExecutor;

  @PostMapping(value = "/{id}/upload")
  private ImageUploadResponse uploadImage(
          @PathVariable("id") String id,
          @RequestParam("file")MultipartFile file) {
    ImageUploadRequest request = new ImageUploadRequest();
    request.setEmployeeId(id);
    request.setFile(file);
    return commandExecutor.executeCommand(ImageUploadCommand.class, request);
  }
}
