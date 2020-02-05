package com.future.clockio.service.impl.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.clockio.entity.company.Photo;
import com.future.clockio.repository.company.PhotoRepository;
import com.future.clockio.request.company.ImageDestroyRequest;
import com.future.clockio.request.company.ImageSaveRequest;
import com.future.clockio.request.company.ImageUploadRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.response.company.ImageUploadResponse;
import com.future.clockio.service.company.EmployeeService;
import com.future.clockio.service.company.ImageService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ImageServiceTest {
  @Mock
  private EmployeeService employeeService;
  @Mock
  private PhotoRepository photoRepository;
  @Mock
  private CommandExecutorService commandExecutor;
  @InjectMocks
  private ImageServiceImpl imageService;
  private static final Photo PHOTO = Photo.builder()
          .employee(EMPLOYEE)
          .publicId(EMP_NAME)
          .mainPhoto(true)
          .build();

  @Test
  public void saveImage() {
    ImageSaveRequest request = new ImageSaveRequest(EMP_ID, "", EMP_NAME);
    when(employeeService.findById(any(UUID.class))).thenReturn(EMPLOYEE);
    BaseResponse response = imageService.saveImage(request);
    Assert.assertTrue(response.isSuccess());
    Assert.assertEquals("Photo saved!", response.getMessage());
  }

  @Test
  public void destroyImage() {
    ImageDestroyRequest request = ImageDestroyRequest.builder()
            .employeeId(EMP_ID)
            .publicId(Collections.singletonList(EMP_NAME))
            .build();
    BaseResponse response = imageService.destroyImage(request);
    Assert.assertTrue(response.isSuccess());
    Assert.assertEquals("Employee Image deleted!",response.getMessage());
    verify(photoRepository, times(1)).deleteById(anyString());
  }

  @Test
  public void uploadImage() {
    ImageUploadRequest request = ImageUploadRequest.builder()
            .employeeId(EMP_ID)
            .faceListId(EMP_NAME)
            .persisted(true)
            .build();
    ImageUploadResponse response = new ImageUploadResponse(EMP_NAME, "", "", "");
    when(employeeService.findById(any(UUID.class))).thenReturn(EMPLOYEE);
    doReturn(response).when(commandExecutor).executeCommand(any(), any(ImageUploadRequest.class));
    BaseResponse res = imageService.uploadImage(request);
    Assert.assertTrue(res.isSuccess());
    Assert.assertEquals("Employee Image uploaded!", res.getMessage());
    verify(photoRepository, times(1)).save(any(Photo.class));
  }
}
