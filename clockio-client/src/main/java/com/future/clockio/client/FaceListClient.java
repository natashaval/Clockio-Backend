package com.future.clockio.client;

import com.future.clockio.client.config.FaceConfiguration;
import com.future.clockio.client.model.request.FaceListRequest;
import com.future.clockio.client.model.response.FaceListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//https://codeboje.de/getting-started-feignclient/

@FeignClient(value = "faceapi",
        url = "${feign.azure.cognitive.url}/facelists/",
configuration = FaceConfiguration.class)
public interface FaceListClient {

  @GetMapping
  public List<FaceListResponse> listFaceList();

  @GetMapping(value = "/{faceListId}")
  public FaceListResponse getFaceList(@PathVariable("faceListId") String faceListId);

  @PutMapping(value = "/{faceListId}")
  public void createFaceList(@PathVariable("faceListId") String faceListId,
                             @RequestBody FaceListRequest request);

  @PatchMapping(value = "/{faceListId}")
  public void updateFaceList(@PathVariable("faceListId") String faceListId,
                             @RequestBody FaceListRequest request);

  @DeleteMapping(value = "/{faceListId}")
  public void deleteFaceList(@PathVariable("faceListId") String faceListId);
}
