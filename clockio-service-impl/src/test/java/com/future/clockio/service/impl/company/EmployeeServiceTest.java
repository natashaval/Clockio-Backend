package com.future.clockio.service.impl.company;

import com.future.clockio.entity.company.Employee;
import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.repository.company.EmployeeRepository;
import com.future.clockio.request.company.EmployeeCreateRequest;
import com.future.clockio.request.core.StatusRequest;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.company.DepartmentService;
import com.future.clockio.service.core.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.future.clockio.service.impl.helper.EntityMock.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {
  @Mock
  private DepartmentService departmentService;

  @Mock
  private UserService userService;

  @Mock
  private EmployeeRepository employeeRepository;

  @InjectMocks
  private EmployeeServiceImpl employeeService;

  private static final EmployeeCreateRequest request = new EmployeeCreateRequest(EMP_NAME, EMP_NAME,
          null, null, null, DEPT_ID, USER_ID);
  private Optional<Employee> empOpt;

  @Before
  public void setUp(){
    empOpt = Optional.of(EMPLOYEE);
  }

  @Test
  public void findById_Found() {
    when(employeeRepository.findById(any(UUID.class))).thenReturn(empOpt);
    Employee res = employeeService.findById(EMP_ID);
    Assert.assertEquals(EMP_ID, res.getId());
    Assert.assertEquals(EMP_NAME, res.getFirstName());
  }

  @Test
  public void findById_NotFound() {
    try {
      employeeService.findById(EMP_ID);
    } catch (DataNotFoundException e) {
      Assert.assertEquals("Employee not found!", e.getMessage());
    }
  }

  @Test
  public void findAll() {
    List<Employee> empList = Collections.singletonList(EMPLOYEE);
    Page<Employee> empPage = new PageImpl<>(empList);
    when(employeeRepository.findAll(any(Pageable.class))).thenReturn(empPage);
    Page<Employee> res = employeeService.findAll(0, 5);
    Assert.assertEquals(empList.size(), res.getTotalElements());
  }

  @Test
  public void createEmployee() {
    when(employeeRepository.save(any(Employee.class))).thenReturn(EMPLOYEE);
    when(userService.findById(any(UUID.class))).thenReturn(USER);
    BaseResponse res = employeeService.createEmployee(request);
    Assert.assertTrue(res.isSuccess());
    Assert.assertEquals("Employee is added!", res.getMessage());
  }

  @Test
  public void updateEmployee_Success() {
    when(employeeRepository.findById(any(UUID.class))).thenReturn(empOpt);
    BaseResponse res = employeeService.updateEmployee(EMP_ID, request);
    Assert.assertTrue(res.isSuccess());
    Assert.assertEquals("Employee is updated!", res.getMessage());
  }

  @Test
  public void updateEmployee_NotFound() {
    try {
      employeeService.updateEmployee(EMP_ID, request);
    } catch (DataNotFoundException e) {
      Assert.assertEquals("Employee not found!", e.getMessage());
    }
  }

  @Test
  public void deleteEmployee_Success() {
    when(employeeRepository.existsById(any(UUID.class))).thenReturn(true);
    BaseResponse res = employeeService.deleteById(EMP_ID);
    Assert.assertTrue(res.isSuccess());
    Assert.assertEquals("Employee is removed!", res.getMessage());
  }

  @Test
  public void deleteEmployee_NotFound() {
    try {
      employeeService.deleteById(EMP_ID);
    } catch (DataNotFoundException e) {
      Assert.assertEquals("Employee not found!", e.getMessage());
    }
  }

  @Test
  public void updateStatus_Success() {
    StatusRequest request = new StatusRequest(EMP_ID, "Online");
    when(employeeRepository.findById(any(UUID.class))).thenReturn(empOpt);
    BaseResponse res = employeeService.updateStatus(request);
    Assert.assertTrue(res.isSuccess());
    Assert.assertEquals("Status updated!", res.getMessage());
  }

  @Test
  public void updateStatus_NotFound() {
    StatusRequest request = new StatusRequest(EMP_ID, "Online");
    try {
      employeeService.updateStatus(request);
    } catch (DataNotFoundException e) {
      Assert.assertEquals("Employee not found!", e.getMessage());
    }
  }
}
