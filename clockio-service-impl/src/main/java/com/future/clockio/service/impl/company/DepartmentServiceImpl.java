package com.future.clockio.service.impl.company;

import com.future.clockio.entity.company.Branch;
import com.future.clockio.entity.company.Department;
import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.exception.InvalidRequestException;
import com.future.clockio.repository.company.BranchRepository;
import com.future.clockio.repository.company.DepartmentRepository;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.company.BranchService;
import com.future.clockio.service.company.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DepartmentServiceImpl implements DepartmentService {

  private DepartmentRepository departmentRepository;

  @Autowired
  public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
    this.departmentRepository = departmentRepository;
  }

  @Override
  public Department findById(UUID id) {
    return departmentRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Department not found!"));
  }

  @Override
  public BaseResponse deleteById(UUID id) {
    Boolean exist = departmentRepository.existsById(id);
    if (exist) {
      departmentRepository.deleteById(id);
      return BaseResponse.success("Department is deleted!");
    } else {
      throw new DataNotFoundException("Department not found!");
    }
  }

  @Override
  public BaseResponse createDepartment(Department department) {
    Optional.of(department)
            .map(departmentRepository::save)
            .orElseThrow(() -> new InvalidRequestException("Failed in create new department!"));
    return BaseResponse.success("Department is added!");
  }

  @Override
  public BaseResponse updateDepartment(UUID id, Department department) {
    Optional.of(id)
            .map(departmentRepository::findById)
            .orElseThrow(() -> new DataNotFoundException("Department not found!"))
            .map(existDepartment -> this.copyProperties(department, existDepartment))
            .map(departmentRepository::save)
            .orElse(department);

    return BaseResponse.success("Department is updated!");
  }

  @Override
  public List<Department> findAll() {
    return departmentRepository.findAll();
  }

  public Department copyProperties(Department department, Department targetDepartment) {
    targetDepartment.setName(department.getName());
    targetDepartment.setBranchId(department.getBranchId());
    return targetDepartment;
  }
}
