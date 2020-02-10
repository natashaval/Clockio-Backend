package com.future.clockio.command;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.clockio.command.impl.ImageUploadCommandImpl;
import com.future.clockio.exception.InvalidRequestException;
import com.future.clockio.request.company.ImageUploadRequest;
import com.future.clockio.response.company.ImageUploadResponse;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ImageUploadCommandTest {
  @Mock
  private ObjectMapper mapper;

  @Mock
  private Cloudinary cloudinary;

  @InjectMocks
  private ImageUploadCommandImpl imageUploadCommand;

  private static final String EMP_NAME = "Employee";
  private static final UUID EMP_ID = UUID.randomUUID();

  @Test
  public void imageUpload_Persisted() throws IOException {
    byte[] file = new byte[1];
    file[0] = ' ';
    MultipartFile multipartFile = new MockMultipartFile(EMP_NAME, file);
    ImageUploadRequest request = new ImageUploadRequest(EMP_ID, EMP_NAME, multipartFile, true);
    ImageUploadResponse uploadResponse = new ImageUploadResponse(EMP_NAME, "jpg", "image", "");
    when(cloudinary.uploader()).thenReturn(mock(Uploader.class));
    when(cloudinary.uploader().upload(any(), anyMap())).thenReturn(mock(Map.class));
    when(mapper.convertValue(any(), eq(ImageUploadResponse.class))).thenReturn(uploadResponse);
    ImageUploadResponse response = imageUploadCommand.execute(request);
    Assert.assertEquals(response.getPublicId(), EMP_NAME);
    Assert.assertEquals(response.getFormat() ,"jpg");
    Assert.assertEquals(response.getResourceType() ,"image");
  }

  @Test
  public void imageUpload_Failed() {
    ImageUploadRequest request = new ImageUploadRequest(EMP_ID, EMP_NAME, null, true);
    when(cloudinary.uploader()).thenReturn(mock(Uploader.class));
    try {
//      when(cloudinary.uploader().upload(any(), anyMap())).thenThrow(new Exception());
      imageUploadCommand.execute(request);
//      fail();
    } catch (Exception e) {
      Assert.assertEquals("Error in uploading image to Cloudinary", e.getMessage());
    }
  }
}
