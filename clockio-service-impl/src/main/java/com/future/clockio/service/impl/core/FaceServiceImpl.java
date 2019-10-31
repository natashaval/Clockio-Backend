package com.future.clockio.service.impl.core;

import com.future.clockio.client.FaceClient;
import com.future.clockio.client.FaceListClient;
import com.future.clockio.client.model.request.AddFaceRequest;
import com.future.clockio.client.model.request.FindSimilarRequest;
import com.future.clockio.client.model.response.AddFaceResponse;
import com.future.clockio.client.model.response.FindSimilarResponse;
import com.future.clockio.command.ImageDestroyCommand;
import com.future.clockio.command.ImageUploadCommand;
import com.future.clockio.entity.company.Employee;
import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.exception.InvalidRequestException;
import com.future.clockio.repository.company.EmployeeRepository;
import com.future.clockio.request.company.ImageDestroyRequest;
import com.future.clockio.request.company.ImageUploadRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.response.company.ImageDestroyResponse;
import com.future.clockio.response.company.ImageUploadResponse;
import com.future.clockio.service.core.CommandExecutorService;
import com.future.clockio.service.core.FaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FaceServiceImpl implements FaceService {
  private EmployeeRepository employeeRepository;
  private FaceListClient faceListClient;
  private FaceClient faceClient;
  private CommandExecutorService commandExecutor;

  @Autowired
  public FaceServiceImpl(EmployeeRepository repository,
                         FaceListClient faceListClient,
                         FaceClient faceClient,
                         CommandExecutorService commandExecutor) {
    this.employeeRepository = repository;
    this.faceListClient = faceListClient;
    this.faceClient = faceClient;
    this.commandExecutor = commandExecutor;
  }

  @Override
  public BaseResponse findSimilar(ImageUploadRequest request) {
    Employee employee = employeeRepository.findById(request.getEmployeeId())
            .orElseThrow(() -> new DataNotFoundException("Employee not found!"));

    request.setFaceListId(employee.getFaceListId());

    // upload image to Cloudinary
    ImageUploadResponse uploadResponse = (ImageUploadResponse) uploadImage(request).getData();

    // insert face to Face - Detect
    String faceId = faceDetect(uploadResponse.getUrl());

    // find similar using Face - Find Similar
    boolean isSimilar = checkSimilarity(faceId, employee.getFaceListId());

    // delete face from Cloudinary
    List<String> publicId = new ArrayList<>();
    publicId.add(uploadResponse.getPublicId());
    ImageDestroyRequest destroyRequest = new ImageDestroyRequest();
    destroyRequest.setEmployeeId(request.getEmployeeId());
    destroyRequest.setPublicId(publicId);
    destroyRequest.setByTag(false);
    deleteImage(destroyRequest);

    return (isSimilar) ? BaseResponse.success("Face Match!") :
            BaseResponse.failed("Face Not Match!");
  }

  @Override
  public BaseResponse uploadImage(ImageUploadRequest request){
    ImageUploadResponse imageResponse;
    try {
      imageResponse =
              commandExecutor.executeCommand(ImageUploadCommand.class, request);
      log.info("Image Upload URL: " + imageResponse.getUrl());
    } catch (Exception e) {
      throw new InvalidRequestException("Failed in uploading image!");
    }
    log.info("Image: " + imageResponse);
    BaseResponse response = BaseResponse.success("Image uploaded!");
    response.setData(imageResponse);
    return response;
  }

  @Override
  public String faceDetect(String url){
    List<AddFaceResponse> response;
    try {
      response = faceClient.faceDetect("recognition_02", true,
              new AddFaceRequest(url));
      log.info("Image Public ID: " + response.get(0).getFaceId());
    } catch (Exception e) {
      throw new InvalidRequestException("Failed in Face Detect!");
    }
    return response.get(0).getFaceId();
  }

  @Override
  public boolean checkSimilarity(String faceId, String faceListId) {
    FindSimilarRequest findSimilarRequest = new FindSimilarRequest();
    findSimilarRequest.setFaceListId(faceListId);
    findSimilarRequest.setFaceId(faceId);
    findSimilarRequest.setMode("matchFace");

    List<FindSimilarResponse> similarResponse = faceClient.findSimilar(findSimilarRequest);
    double averageConfidence = 0.0;
    for (FindSimilarResponse response: similarResponse) {
      averageConfidence += response.getConfidence();
    }
    return averageConfidence/similarResponse.size() >= 0.90;
  }

  @Override
  public BaseResponse deleteImage(ImageDestroyRequest request) {
    ImageDestroyResponse destroyResponse;
    try {
      destroyResponse =
              commandExecutor.executeCommand(ImageDestroyCommand.class, request);
      log.info("Delete image: " + destroyResponse.getPartial());
    } catch (Exception e) {
      throw new InvalidRequestException("Failed in Delete Image!");
    }
    BaseResponse response = BaseResponse.success("Image Deleted");
    response.setData(destroyResponse);
    return response;
  }
}