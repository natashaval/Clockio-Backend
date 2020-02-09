package com.future.clockio.response.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDestroyResponse {
  private Map<String, String> deleted;
  private String partial;
}
