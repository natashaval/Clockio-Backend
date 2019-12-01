package com.future.clockio.controller.core;

import com.future.clockio.entity.core.Location;
import com.future.clockio.request.core.LocationHistoryRequest;
import com.future.clockio.request.core.LocationRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.core.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/location")
public class LocationController {
  @Autowired
  private LocationService locationService;

  @GetMapping("/{id}/history")
  public List<Location> getLocationHistory(@PathVariable("id") UUID id,
                                           @RequestParam("y") int year,
                                           @RequestParam("m") int month,
                                           @RequestParam("d") int day) {
    LocationHistoryRequest request = new LocationHistoryRequest();
    request.setEmployeeId(id);
    request.setYear(year);
    request.setMonth(month);
    request.setDay(day);
    return locationService.findEmployeeLocation(request);
  }

  @GetMapping("/{id}") // location id
  public Location getLocation(@PathVariable("id") UUID id) {
    return locationService.findById(id);
  }

  @PostMapping
  public BaseResponse addLocation(@RequestBody LocationRequest request) {
    return locationService.addLocation(request);
  }

}
