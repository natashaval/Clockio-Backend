package com.future.clockio.controller.core;

import com.future.clockio.client.FaceListClient;
import com.future.clockio.client.model.request.AddFaceRequest;
import com.future.clockio.client.model.request.FaceListRequest;
import com.future.clockio.client.model.response.AddFaceResponse;
import com.future.clockio.client.model.response.FaceListResponse;
import com.future.clockio.response.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/facelist")
public class FaceListController {

  @Autowired
  private FaceListClient client;

  @GetMapping
  public List<FaceListResponse> getFaceList(){
    return client.listFaceList();
  }

  @GetMapping(value = "/{faceListId}")
  public FaceListResponse getFaceList(@PathVariable("faceListId") String faceListId) {
    return client.getFaceList(faceListId, true);
  }

  @PutMapping(value = "/{faceListId}")
  public ResponseEntity<BaseResponse> createFaceList(@PathVariable("faceListId") String faceListId,
                                                     @RequestBody FaceListRequest request) {
    client.createFaceList(faceListId, request);
    return ResponseEntity.ok(BaseResponse.success("Face List created!"));
  }

  @PatchMapping(value = "/{faceListId}")
  public ResponseEntity<BaseResponse> updateFaceList(@PathVariable("faceListId") String faceListId,
                             @RequestBody FaceListRequest request) {
    client.updateFaceList(faceListId, request);
    return ResponseEntity.ok(BaseResponse.success("Face List updated!"));
  }

  @DeleteMapping(value = "/{faceListId}")
  public ResponseEntity<BaseResponse> deleteFaceList(@PathVariable(
          "faceListId") String faceListId) {
    client.deleteFaceList(faceListId);
    return ResponseEntity.ok(BaseResponse.success("Face List deleted!"));
  }

  @PostMapping(value = "/{faceListId}/persistedFaces")
  public BaseResponse addFace(@PathVariable("faceListId") String faceListId,
                                 @RequestBody AddFaceRequest request) {
    AddFaceResponse faceResponse = client.addFace(faceListId, request);
    BaseResponse response = BaseResponse.success("Face Added!");
//    response.getData().put("persistedFaceId",
//            faceResponse.getPersistedFaceId());
    response.setData(faceResponse);
    return response;
  }
}
