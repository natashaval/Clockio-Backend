package com.future.clockio.command.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.clockio.command.ImageDestroyCommand;
import com.future.clockio.exception.InvalidRequestException;
import com.future.clockio.request.company.ImageDestroyRequest;
import com.future.clockio.response.company.ImageDestroyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class ImageDestroyCommandImpl implements ImageDestroyCommand {

  @Autowired
  private Cloudinary cloudinary;

  @Autowired
  private ObjectMapper mapper;

  @Override
  public ImageDestroyResponse execute(ImageDestroyRequest request) {
    ImageDestroyResponse response;
    try {
      Map result;
      if (request.isByTag()) {
        log.info("Delete by TAG");
        result = cloudinary.api().deleteResourcesByTag(request.getTag(), ObjectUtils.emptyMap());
      }
      else { // delete by List of public id's
        log.info("Delete by PUBLIC IDs");
        result = cloudinary.api().deleteResources(request.getPublicId(), ObjectUtils.emptyMap());
      }
      response = mapper.convertValue(result, ImageDestroyResponse.class);
    } catch (Exception e) {
      log.error("Failed to delete resources in Cloudinary", e.getMessage());
      e.printStackTrace();
      throw new InvalidRequestException("Failed to delete resources in Cloudinary");
    }

    return response;
  }
}
