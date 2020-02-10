package com.future.clockio.service.impl.core;

import com.future.clockio.client.FaceClient;
import com.future.clockio.client.FaceListClient;
import com.future.clockio.client.model.request.AddFaceRequest;
import com.future.clockio.client.model.request.FindSimilarRequest;
import com.future.clockio.client.model.response.AddFaceResponse;
import com.future.clockio.client.model.response.FindSimilarResponse;
import com.future.clockio.command.ImageDestroyCommand;
import com.future.clockio.exception.InvalidRequestException;
import com.future.clockio.request.company.ImageDestroyRequest;
import com.future.clockio.request.company.ImageUploadRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.response.company.ImageDestroyResponse;
import com.future.clockio.response.company.ImageUploadResponse;
import com.future.clockio.service.company.EmployeeService;
import com.future.clockio.service.core.CommandExecutorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.UUID;

import static com.future.clockio.service.impl.helper.EntityMock.*;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FaceServiceTest {
  @Mock private EmployeeService employeeService;
  @Mock private FaceListClient faceListClient;
  @Mock private FaceClient faceClient;
  @Mock private CommandExecutorService commandExecutor;

  @InjectMocks private FaceServiceImpl faceService;

  private static final String TAG = "tag";
  private static final String URL = "http://picsum.photos";
  private static final ImageDestroyRequest IMAGE_DESTROY_REQUEST = ImageDestroyRequest.builder()
          .employeeId(EMP_ID)
          .publicId(Collections.singletonList(EMP_NAME))
          .tag(TAG)
          .byTag(true)
          .build();
  private static final ImageUploadRequest IMAGE_UPLOAD_REQUEST = ImageUploadRequest.builder()
          .employeeId(EMP_ID)
          .faceListId(EMP_NAME)
          .persisted(true)
          .build();
  private static final ImageUploadResponse IMAGE_UPLOAD_RESPONSE = ImageUploadResponse.builder()
          .publicId(EMP_NAME)
          .build();
  private static final ImageDestroyResponse IMAGE_DESTROY_RESPONSE = new ImageDestroyResponse();
  private static final AddFaceResponse ADD_FACE_RESPONSE = new AddFaceResponse(EMP_NAME, EMP_NAME,
          "recognition_02");
  private static final FindSimilarResponse FIND_SIMILAR_RESPONSE = new FindSimilarResponse(EMP_NAME, 1.0);
  private static final FindSimilarResponse FIND_NOT_SIMILAR_RESPONSE =
          new FindSimilarResponse(EMP_NAME, 0.1);


  @Test
  public void deleteImage_Failed() {
    try {
      faceService.deleteImage(IMAGE_DESTROY_REQUEST);
      fail();
    } catch (InvalidRequestException e) {
      Assert.assertEquals("Failed in delete image!", e.getMessage());
    }
  }

  @Test
  public void deleteImage_Success() {
    when(commandExecutor.executeCommand(ImageDestroyCommand.class, IMAGE_DESTROY_REQUEST))
            .thenReturn(IMAGE_DESTROY_RESPONSE);
    BaseResponse response = faceService.deleteImage(IMAGE_DESTROY_REQUEST);
    Assert.assertTrue(response.isSuccess());
    Assert.assertEquals("Image deleted!", response.getMessage());
  }

  @Test
  public void checkSimilarity() {
//    FindSimilarRequest findSimilarRequest = new FindSimilarRequest(EMP_NAME, EMP_NAME, "matchFace");
    when(faceClient.findSimilar(any(FindSimilarRequest.class)))
            .thenReturn(Collections.singletonList(FIND_SIMILAR_RESPONSE));
    boolean result = faceService.checkSimilarity(EMP_NAME, EMP_NAME);
    Assert.assertTrue(result);
  }

  @Test
  public void faceDetect_Failed() {
    try {
      faceService.faceDetect(URL);
    } catch (InvalidRequestException e) {
      Assert.assertEquals("Failed in face detect!", e.getMessage());
    }
  }

  @Test
  public void faceDetect_Success() {
    when(faceClient.faceDetect(anyString(), anyBoolean(), any(AddFaceRequest.class)))
            .thenReturn(Collections.singletonList(ADD_FACE_RESPONSE));
    String result = faceService.faceDetect(URL);
    Assert.assertEquals(EMP_NAME, result);
  }

  @Test
  public void uploadImage_Failed() {
    try {
      faceService.uploadImage(IMAGE_UPLOAD_REQUEST);
    } catch (InvalidRequestException e){
      Assert.assertEquals("Failed in uploading image!", e.getMessage());
    }
  }

  @Test
  public void uploadImage_Success() {
    when(commandExecutor.executeCommand(any(), any(ImageUploadRequest.class)))
            .thenReturn(IMAGE_UPLOAD_RESPONSE);
    BaseResponse response = faceService.uploadImage(IMAGE_UPLOAD_REQUEST);
    Assert.assertTrue(response.isSuccess());
    Assert.assertEquals("Image uploaded!", response.getMessage());
    Assert.assertEquals(IMAGE_UPLOAD_RESPONSE, response.getData());
  }

  @Test
  public void detectSimilar_Match() {
    when(employeeService.findById(any(UUID.class))).thenReturn(EMPLOYEE);
    when(commandExecutor.executeCommand(any(), any(ImageUploadRequest.class)))
            .thenReturn(IMAGE_UPLOAD_RESPONSE);
    when(faceClient.faceDetect(anyString(), anyBoolean(), any(AddFaceRequest.class)))
            .thenReturn(Collections.singletonList(ADD_FACE_RESPONSE));
    when(faceClient.findSimilar(any(FindSimilarRequest.class)))
            .thenReturn(Collections.singletonList(FIND_SIMILAR_RESPONSE));
    when(commandExecutor.executeCommand(any(), any(ImageDestroyRequest.class)))
            .thenReturn(IMAGE_DESTROY_RESPONSE);
    BaseResponse response = faceService.findSimilar(IMAGE_UPLOAD_REQUEST);
    Assert.assertTrue(response.isSuccess());
    Assert.assertEquals("Face match!", response.getMessage());
  }

  @Test
  public void detectSimilar_NotMatch() {
    when(employeeService.findById(any(UUID.class))).thenReturn(EMPLOYEE);
    when(commandExecutor.executeCommand(any(), any(ImageUploadRequest.class)))
            .thenReturn(IMAGE_UPLOAD_RESPONSE);
    when(faceClient.faceDetect(anyString(), anyBoolean(), any(AddFaceRequest.class)))
            .thenReturn(Collections.singletonList(ADD_FACE_RESPONSE));
    when(faceClient.findSimilar(any(FindSimilarRequest.class)))
            .thenReturn(Collections.singletonList(FIND_NOT_SIMILAR_RESPONSE));
    when(commandExecutor.executeCommand(any(), any(ImageDestroyRequest.class)))
            .thenReturn(IMAGE_DESTROY_RESPONSE);
    BaseResponse response = faceService.findSimilar(IMAGE_UPLOAD_REQUEST);
    Assert.assertFalse(response.isSuccess());
    Assert.assertEquals("Face not match!", response.getMessage());
  }
}
