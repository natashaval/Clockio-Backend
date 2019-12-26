package com.future.clockio.service.company;

import com.future.clockio.entity.company.Employee;
import com.future.clockio.request.company.EmployeeCreateRequest;
import com.future.clockio.request.core.StatusRequest;
import com.future.clockio.response.base.BaseResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {
  Employee findById(UUID id);

  BaseResponse deleteById(UUID id);

  BaseResponse createEmployee(EmployeeCreateRequest employee);

  BaseResponse updateEmployee(UUID id, EmployeeCreateRequest employee);

  Page<Employee> findAll(int page, int size);

  BaseResponse updateStatus(StatusRequest request);
}
