package com.future.clockio.service.company;

import com.future.clockio.entity.company.Employee;
import com.future.clockio.request.company.EmployeeCreateRequest;
import com.future.clockio.response.base.BaseResponse;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {
  Employee findById(UUID id);

  BaseResponse deleteById(UUID id);

  BaseResponse createEmployee(EmployeeCreateRequest employee);

  BaseResponse updateEmployee(UUID id, EmployeeCreateRequest employee);

  List<Employee> findAll();
}
