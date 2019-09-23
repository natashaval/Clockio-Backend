package com.future.clockio.client;

import com.future.clockio.client.config.FaceConfiguration;
import com.future.clockio.client.model.FaceListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

//https://codeboje.de/getting-started-feignclient/

@FeignClient(value = "faceapi",
        url = "${feign.azure.cognitive.url}",
configuration = FaceConfiguration.class)
public interface FaceListClient {

  @RequestMapping(value = "/facelists", method = RequestMethod.GET)
  public List<FaceListResponse> getFaceList();
}
