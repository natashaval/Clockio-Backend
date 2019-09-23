package com.future.clockio.service.company;

import com.future.clockio.entity.company.Employee;
import com.future.clockio.request.company.EmployeeCreateRequest;
import com.future.clockio.response.base.BaseResponse;

import java.util.List;

public interface EmployeeService {
  Employee findById(String id);

  BaseResponse deleteById(String id);

  BaseResponse createEmployee(EmployeeCreateRequest employee);

  BaseResponse updateEmployee(String id, EmployeeCreateRequest employee);

  List<Employee> findAll();
}
