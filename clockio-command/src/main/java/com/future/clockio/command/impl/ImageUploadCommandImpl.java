package com.future.clockio.command.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.clockio.command.ImageUploadCommand;
import com.future.clockio.request.ImageUploadRequest;
import com.future.clockio.response.company.ImageUploadResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class ImageUploadCommandImpl implements ImageUploadCommand {

  @Autowired
  private Cloudinary cloudinary;

  @Autowired
  private ObjectMapper mapper;

  @Override
  public ImageUploadResponse execute(ImageUploadRequest request) {
    ImageUploadResponse response = new ImageUploadResponse();
    try {
      Transformation transformation = new Transformation()
              .width(400).height(600).gravity("face").crop("crop");
      Map result = cloudinary.uploader()
              .upload(request.getFile().getBytes(), ObjectUtils.asMap(
                      "public_id", request.getEmployeeId(),
                      "folder", "Profile",
                      "unique_filename", true,
                      "transformation", transformation
              ));
      response = mapper.convertValue(result, ImageUploadResponse.class);
    } catch (Exception e) {
      log.error("Error in uploading image to Cloudinary", e.getMessage());
    }
    return response;
  }
}
