package com.future.clockio.service.impl.core;

import com.future.clockio.client.FaceClient;
import com.future.clockio.client.FaceListClient;
import com.future.clockio.client.model.request.AddFaceRequest;
import com.future.clockio.client.model.request.FindSimilarRequest;
import com.future.clockio.client.model.response.AddFaceResponse;
import com.future.clockio.client.model.response.FindSimilarResponse;
import com.future.clockio.command.ImageUploadCommand;
import com.future.clockio.entity.company.Employee;
import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.repository.company.EmployeeRepository;
import com.future.clockio.request.company.ImageUploadRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.response.company.ImageUploadResponse;
import com.future.clockio.service.core.CommandExecutorService;
import com.future.clockio.service.core.FaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    // upload image to Cloudinary
    String uploadImageUrl = uploadImage(request);

    // insert face to Face - Detect
    String faceId = faceDetect(uploadImageUrl);

    // find similar using Face - Find Similar
    FindSimilarRequest findSimilarRequest = new FindSimilarRequest();
    findSimilarRequest.setFaceListId(request.getFaceListId());
    findSimilarRequest.setFaceId(faceId);
    findSimilarRequest.setMode("matchFace");

    List<FindSimilarResponse> similarResponse = faceClient.findSimilar(findSimilarRequest);
    double averageConfidence = 0.0;
    for (FindSimilarResponse response: similarResponse) {
      averageConfidence += response.getConfidence();
    }
    boolean isMatch = averageConfidence/similarResponse.size() >= 90.0;
    return (isMatch) ? BaseResponse.success("Face Match!") :
            BaseResponse.failed("Face Not Match!");
  }

  private String uploadImage(ImageUploadRequest request){
    Employee employee = employeeRepository.findById(request.getEmployeeId())
            .orElseThrow(() -> new DataNotFoundException("Employee not found!"));

    request.setFaceListId(employee.getFaceListId());
    request.setPersisted(false);
    ImageUploadResponse imageResponse =
            commandExecutor.executeCommand(ImageUploadCommand.class, request);
    log.info("Presence: " + imageResponse);
    return imageResponse.getUrl();
  }

  private String faceDetect(String url){
    List<AddFaceResponse> response = faceClient.faceDetect("recognition_02", true,
            new AddFaceRequest(url));
    return response.get(0).getFaceId();
  }
}