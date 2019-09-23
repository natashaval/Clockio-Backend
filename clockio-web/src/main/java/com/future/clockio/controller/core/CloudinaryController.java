package com.future.clockio.controller.core;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
public class CloudinaryController {
  @Autowired
  private Cloudinary cloudinary;

  @PostMapping("/api/cloudinary/image")
  private Object uploadImage() throws IOException {
    File file = new File("clockio-web/src/main/resources/1-zoe(2).jpg");
    if (file.exists()){
      Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
      return uploadResult;
    }
    return "False to upload Image!";
  }

  @GetMapping("/api/cloudinary/image")
  private Object getImage(){
    return cloudinary.url().imageTag("sample.jpg");
  }
}
