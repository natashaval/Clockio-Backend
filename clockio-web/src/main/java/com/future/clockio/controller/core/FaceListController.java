package com.future.clockio.controller.core;

import com.future.clockio.client.FaceListClient;
import com.future.clockio.client.model.FaceListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/facelist")
public class FaceListController {

  @Autowired
  private FaceListClient faceListClient;

  @GetMapping
  public List<FaceListResponse> getFaceList(){
    return faceListClient.getFaceList();
  }
}
