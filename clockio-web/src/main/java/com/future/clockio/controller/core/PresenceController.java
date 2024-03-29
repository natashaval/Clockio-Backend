package com.future.clockio.controller.core;

import com.future.clockio.entity.core.Presence;
import com.future.clockio.request.company.ImageUploadRequest;
import com.future.clockio.request.core.PresenceRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.company.ImageService;
import com.future.clockio.service.core.PresenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/presence")
public class PresenceController {

  private PresenceService presenceService;
  private ImageService imageService;

  @Autowired
  public PresenceController(PresenceService presenceService, ImageService imageService) {
    this.presenceService = presenceService;
    this.imageService = imageService;
  }

  @GetMapping
  public List<Presence> findAll() {
    return presenceService.findAll();
  }

  @PostMapping(value = "/{id}/checkin") // employee Id
  public BaseResponse checkIn(@PathVariable("id") UUID id,
                              @RequestBody PresenceRequest request) {
    request.setEmployeeId(id);
    return presenceService.checkIn(request);
  }

  @PostMapping(value = "/{presenceId}/checkout") // presence Id
  public BaseResponse checkOut(@PathVariable("presenceId") UUID presenceId,
                               @RequestBody PresenceRequest request) {
    request.setPresenceId(presenceId);
    return presenceService.checkOut(request);
  }

  @PostMapping(value = "/{id}/upload") // employee Id
  public BaseResponse checkInPhotoUpload(
          @PathVariable("id") UUID id,
          @RequestParam("photo") MultipartFile photo) {
    ImageUploadRequest request = new ImageUploadRequest();
    request.setEmployeeId(id);
    request.setFile(photo);
    request.setPersisted(false);
    return imageService.uploadImage(request);
  }

}
