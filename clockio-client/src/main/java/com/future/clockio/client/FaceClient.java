package com.future.clockio.client;

import com.future.clockio.client.config.FaceConfiguration;
import com.future.clockio.client.model.request.AddFaceRequest;
import com.future.clockio.client.model.request.FindSimilarRequest;
import com.future.clockio.client.model.response.AddFaceResponse;
import com.future.clockio.client.model.response.FindSimilarResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="faceApi",
        url = "${feign.azure.cognitive.url}",
        configuration = FaceConfiguration.class)
public interface FaceClient {
  @PostMapping(value = "/detect")
  public List<AddFaceResponse> faceDetect(@RequestParam("recognitionModel") String recognitionModel,
                                    @RequestParam("returnRecognitionModel") boolean returnRecognitionModel,
                                    @RequestBody AddFaceRequest request);

  @PostMapping(value = "/findsimilars")
  public List<FindSimilarResponse> findSimilar(@RequestBody FindSimilarRequest request);
}
