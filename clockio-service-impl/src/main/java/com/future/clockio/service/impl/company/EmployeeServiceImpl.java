package com.future.clockio.service.impl.company;

import com.future.clockio.entity.company.Employee;
import com.future.clockio.entity.core.User;
import com.future.clockio.entity.constant.EStatus;
import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.exception.InvalidRequestException;
import com.future.clockio.repository.company.DepartmentRepository;
import com.future.clockio.repository.company.EmployeeRepository;
import com.future.clockio.repository.core.UserRepository;
import com.future.clockio.request.company.EmployeeCreateRequest;
import com.future.clockio.request.core.StatusRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.company.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  private EmployeeRepository employeeRepository;
  private DepartmentRepository departmentRepository;
  private UserRepository userRepository;

  @Autowired
  public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                             DepartmentRepository departmentRepository,
                             UserRepository userRepository) {
    this.employeeRepository = employeeRepository;
    this.departmentRepository = departmentRepository;
    this.userRepository = userRepository;
  }

  @Override
  public Employee findById(UUID id) {
    return employeeRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Employee not found!"));
  }

  @Override
  public BaseResponse deleteById(UUID id) {
    Employee exist = employeeRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Employee not found!"));
    if (exist != null) {
      employeeRepository.deleteById(id);
      return BaseResponse.success("Employee is removed!");
    }
    else throw new InvalidRequestException("Failed in remove employee!");  }

  @Override
  public BaseResponse createEmployee(EmployeeCreateRequest employee) {
    Employee newEmployee = Optional.of(this.copyProperties(employee, new Employee(), false))
            .map(employeeRepository::save)
            .orElseThrow(() -> new InvalidRequestException("Failed in adding new employee!"));
    User user = userRepository.findById(employee.getUserId())
            .orElseThrow(() -> new InvalidRequestException("User not found!"));
    user.setEmployeeId(newEmployee.getId());
    userRepository.save(user);
    return BaseResponse.success("Employee is added!");

  }

  @Override
  public BaseResponse updateEmployee(UUID id, EmployeeCreateRequest employee) {
    Optional.of(id)
            .map(employeeRepository::findById)
            .orElseThrow(() -> new DataNotFoundException("Employee not found!"))
            .map(exist -> this.copyProperties(employee, exist, true))
            .map(employeeRepository::save);
//            .orElse(exist);

    return BaseResponse.success("Employee is updated!");
  }

  @Override
  public List<Employee> findAll() {
    return employeeRepository.findAll();
  }

  @Override
  public BaseResponse updateStatus(StatusRequest request) {
    Employee employee = findById(request.getEmployeeId());
    employee.setStatus(EStatus.valueOf(request.getStatus().toUpperCase()).name());
    employeeRepository.save(employee);
    return BaseResponse.success("Status updated!");
  }

  private Employee copyProperties(EmployeeCreateRequest request,
                                  Employee targetEmployee,
                                  boolean isUpdate) {
    targetEmployee.setFirstName(request.getFirstName());
    targetEmployee.setLastName(request.getLastName());
    targetEmployee.setEmail(request.getEmail());
    targetEmployee.setPhone(request.getPhone());
//    targetEmployee.setPhotoUrl(request.getPhotoUrl());
    if (!isUpdate) targetEmployee.setFaceListId(
            request.getLastName().toLowerCase() + "_" + request.getFirstName().toLowerCase()
    ); // set face list id

//    if (targetEmployee.getBranch() == null ||
//            !request.getBranchId().equals(targetEmployee.getBranch().getId())) {
//      targetEmployee.setBranch(branchRepository.findById(request.getBranchId())
//              .orElseThrow(() -> new DataNotFoundException("Branch not found!"))
//      );
//    }

    if (targetEmployee.getDepartment() == null ||
            !request.getDepartmentId().equals(targetEmployee.getDepartment().getId())) {
      targetEmployee.setDepartment(departmentRepository.findById(request.getDepartmentId())
              .orElseThrow(() -> new DataNotFoundException("Department not found!"))
      );
    }

    return targetEmployee;
  }
}
