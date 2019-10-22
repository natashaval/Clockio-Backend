package com.future.clockio.service.company;

import com.future.clockio.entity.company.Branch;
import com.future.clockio.entity.company.Department;
import com.future.clockio.response.base.BaseResponse;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {
  Department findById(UUID id);

  BaseResponse deleteById(UUID id);

  BaseResponse createDepartment(Department department);

  BaseResponse updateDepartment(UUID id, Department department);

  List<Department> findAll();
}
