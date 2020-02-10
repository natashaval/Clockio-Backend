package com.future.clockio.controller.company;

import com.future.clockio.entity.company.Employee;
import com.future.clockio.request.company.EmployeeCreateRequest;
import com.future.clockio.request.core.StatusRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.company.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/employees")
public class EmployeeController {

  private EmployeeService employeeService;

  @Autowired
  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping
  public Page<Employee> getEmployees(
          @RequestParam(value = "page", required = false, defaultValue = "0") int page,
          @RequestParam(value = "size", required = false, defaultValue = "10") int size)
  {return employeeService.findAll(page, size);}

  @GetMapping(value = "/{id}")
  public Employee getEmployee(@PathVariable("id") UUID id) { return employeeService.findById(id);}

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BaseResponse createEmployee(@RequestBody EmployeeCreateRequest request) {
    return employeeService.createEmployee(request);
  }

  @PutMapping(value = "/{id}")
  public BaseResponse updateEmployee(@PathVariable("id") UUID id,
                                     @RequestBody EmployeeCreateRequest request) {
    return employeeService.updateEmployee(id, request);
  }

  @DeleteMapping(value = "/{id}")
  public BaseResponse deleteEmployee(@PathVariable("id") UUID id) {
    return employeeService.deleteById(id);
  }

  @PostMapping("/{id}/status")
  public BaseResponse updateStatus(@PathVariable("id") UUID id, @RequestParam String status) {
    StatusRequest request = new StatusRequest(id, status);
    return employeeService.updateStatus(request);
  }

}
