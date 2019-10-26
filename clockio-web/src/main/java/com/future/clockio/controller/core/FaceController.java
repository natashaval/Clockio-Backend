package com.future.clockio.controller.core;

import com.future.clockio.client.FaceClient;
import com.future.clockio.client.model.request.AddFaceRequest;
import com.future.clockio.client.model.request.FindSimilarRequest;
import com.future.clockio.client.model.response.AddFaceResponse;
import com.future.clockio.client.model.response.FindSimilarResponse;
import com.future.clockio.request.company.ImageUploadRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.core.FaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/face")
public class FaceController {
  @Autowired
  private FaceService faceService;

  @Autowired
  private FaceClient faceClient;

  @PostMapping(value = "/detect")
  public List<AddFaceResponse> faceDetect(@RequestBody AddFaceRequest request) {
    return faceClient.faceDetect("recognition_02", true, request);
  }

  @PostMapping(value = "/{id}/verify")
  public BaseResponse findSimilar(@PathVariable("id") UUID id,
                                  @RequestParam("photo") MultipartFile photo) {
    ImageUploadRequest request = new ImageUploadRequest();
    request.setEmployeeId(id);
    request.setFile(photo);
    request.setPersisted(false);
    return faceService.findSimilar(request);
  }
}
