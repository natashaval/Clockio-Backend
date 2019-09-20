package com.future.clockio.service.company;

import com.future.clockio.entity.company.Branch;
import com.future.clockio.entity.company.Department;
import com.future.clockio.response.base.BaseResponse;

import java.util.List;

public interface DepartmentService {
  Department findById(String id);

  BaseResponse deleteById(String id);

  BaseResponse createDepartment(Department department);

  BaseResponse updateDepartment(String id, Department department);

  List<Department> findAll();
}
