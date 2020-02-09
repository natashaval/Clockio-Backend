package com.future.clockio.command;

import com.cloudinary.Api;
import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.clockio.command.impl.ImageDestroyCommandImpl;
import com.future.clockio.exception.InvalidRequestException;
import com.future.clockio.request.company.ImageDestroyRequest;
import com.future.clockio.response.company.ImageDestroyResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ImageDestroyCommandTest {
  @Mock
  private ObjectMapper mapper;

  @Mock
  private Cloudinary cloudinary;

  @InjectMocks
  private ImageDestroyCommandImpl imageDestroyCommand;

  private static final String EMP_NAME = "Employee";
  private static final UUID EMP_ID = UUID.randomUUID();
  private static final ImageDestroyRequest IMAGE_DESTROY_REQUEST = new ImageDestroyRequest(EMP_ID,
          Collections.singletonList(EMP_NAME), EMP_NAME, true);

  @Test
  public void destroyImage_ByTag() throws Exception {
    Map<String, String> deletedMap = new HashMap<>();
    deletedMap.put(EMP_NAME, "deleted");
    Map<String, Object> map = new HashMap<>();
    map.put("deleted", deletedMap);
    map.put("partial", false);
    ImageDestroyResponse destroyResponse = new ImageDestroyResponse(deletedMap, "false");
    when(cloudinary.api()).thenReturn(mock(Api.class));
    when(cloudinary.api().deleteResourcesByTag(anyString(), anyMap())).thenReturn(mock(ApiResponse.class));
    when(mapper.convertValue(any(), eq(ImageDestroyResponse.class))).thenReturn(destroyResponse);
    ImageDestroyResponse response = imageDestroyCommand.execute(IMAGE_DESTROY_REQUEST);
    Assert.assertEquals(response.getPartial(), "false");
  }

  @Test
  public void destroyImage_Failed() {
    when(cloudinary.api()).thenReturn(mock(Api.class));
    try {
      when(cloudinary.api().deleteResourcesByTag(anyString(), anyMap())).thenThrow(new Exception());
      imageDestroyCommand.execute(IMAGE_DESTROY_REQUEST);
    } catch (Exception e) {
      Assert.assertEquals("Failed to delete resources in Cloudinary", e.getMessage());
    }
  }
}
