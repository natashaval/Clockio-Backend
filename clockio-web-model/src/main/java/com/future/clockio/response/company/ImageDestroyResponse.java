package com.future.clockio.response.company;

import lombok.Data;

import java.util.Map;

@Data
public class ImageDestroyResponse {
  private Map<String, String> deleted;
  private String partial;
}
