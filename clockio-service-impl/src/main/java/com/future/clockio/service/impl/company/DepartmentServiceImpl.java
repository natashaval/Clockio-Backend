package com.future.clockio.service.impl.company;

import com.future.clockio.entity.company.Department;
import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.exception.InvalidRequestException;
import com.future.clockio.repository.company.DepartmentRepository;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.company.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {
  private DepartmentRepository departmentRepository;

  @Autowired
  public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
    this.departmentRepository = departmentRepository;
  }

  @Override
  public Department findById(String id) {
    return departmentRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Department not found!"));
  }

  @Override
  public BaseResponse deleteById(String id) {
    Department exist = departmentRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Department not found!"));
    if (exist != null) {
      departmentRepository.deleteById(id);
      return BaseResponse.success("Department is deleted!");
    }
    else throw new InvalidRequestException("Failed in delete Department!");
  }

  @Override
  public BaseResponse createDepartment(Department Department) {
    Optional.of(Department)
            .map(departmentRepository::save)
            .orElseThrow(() -> new InvalidRequestException("Failed in create new Department!"));
    return BaseResponse.success("Department is added!");
  }

  @Override
  public BaseResponse updateDepartment(String id, Department Department) {
    Optional.of(id)
            .map(departmentRepository::findById)
            .orElseThrow(() -> new DataNotFoundException("Department not found!"))
            .map(existDepartment -> this.copyProperties(Department, existDepartment))
            .map(departmentRepository::save)
            .orElse(Department);

    return BaseResponse.success("Department is updated!");
  }

  @Override
  public List<Department> findAll() {
    return departmentRepository.findAll();
  }

  private Department copyProperties(Department department, Department targetDepartment) {
    targetDepartment.setName(department.getName());
    targetDepartment.setBranch(department.getBranch());
    return targetDepartment;
  }
}
