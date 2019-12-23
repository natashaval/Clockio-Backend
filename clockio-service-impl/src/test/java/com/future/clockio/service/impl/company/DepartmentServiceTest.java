package com.future.clockio.service.impl.company;

import com.future.clockio.entity.company.Department;
import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.exception.InvalidRequestException;
import com.future.clockio.repository.company.DepartmentRepository;
import com.future.clockio.response.base.BaseResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentServiceTest {
  @Mock
  private DepartmentRepository departmentRepository;

  @InjectMocks
  private DepartmentServiceImpl departmentService;


  private static final String DEPT_NAME = "Department";
  private static final UUID DEPT_ID = UUID.randomUUID();
  private static final String DEPT_BRANCH_ID = UUID.randomUUID().toString();
  private static final Department DEPARTMENT = Department.builder()
          .id(DEPT_ID)
          .name(DEPT_NAME)
          .branchId(DEPT_BRANCH_ID).build();
  private Optional<Department> deptOpt;

  @Before
  public void setUp() {
    deptOpt = Optional.of(DEPARTMENT);
  }

  @Test
  public void findById_Found() {
    when(departmentRepository.findById(any(UUID.class))).thenReturn(deptOpt);
    Department department = departmentService.findById(DEPT_ID);
    Assert.assertEquals(DEPT_ID, department.getId());
    Assert.assertEquals(DEPT_NAME, department.getName());
    Assert.assertEquals(DEPT_BRANCH_ID, department.getBranchId());
  }

  @Test
  public void findById_NotFound() {
    try {
      departmentService.findById(DEPT_ID);
    } catch (DataNotFoundException e) {
      Assert.assertEquals("Department not found!", e.getMessage());
    }
  }

  @Test
  public void deleteById_Success() {
    when(departmentRepository.existsById(any(UUID.class))).thenReturn(true);
    BaseResponse res = departmentService.deleteById(DEPT_ID);
    Assert.assertTrue(res.isSuccess());
    Assert.assertEquals("Department is deleted!", res.getMessage());
  }

  @Test
  public void deleteById_Failed() {
    try {
      departmentService.deleteById(DEPT_ID);
    } catch (DataNotFoundException e) {
      Assert.assertEquals("Department not found!", e.getMessage());
    }
  }

  @Test
  public void createDept_Success() {
    when(departmentRepository.save(any(Department.class))).thenReturn(DEPARTMENT);
    BaseResponse res = departmentService.createDepartment(DEPARTMENT);
    Assert.assertTrue(res.isSuccess());
    Assert.assertEquals("Department is added!", res.getMessage());
  }

  @Test
  public void createDept_Failed() {
    when(departmentRepository.save(any(Department.class))).thenReturn(null);
    try {
      departmentService.createDepartment(DEPARTMENT);
    } catch (InvalidRequestException e) {
      Assert.assertEquals("Failed in create new department!", e.getMessage());
    }
  }

  @Test
  public void updateDept_NotFound() {
    try {
      departmentService.updateDepartment(DEPT_ID, DEPARTMENT);
    } catch (DataNotFoundException e) {
      Assert.assertEquals("Department not found!", e.getMessage());
    }
  }

  @Test
  public void updateDept_Success() {
    String deptNewName = "new Department";
    Department target = new Department(DEPT_ID, deptNewName, DEPT_BRANCH_ID);
    when(departmentRepository.findById(DEPT_ID)).thenReturn(deptOpt);
    BaseResponse res = departmentService.updateDepartment(DEPT_ID, target);
    Assert.assertTrue(res.isSuccess());
    Assert.assertEquals("Department is updated!", res.getMessage());
  }

  @Test
  public void findAll() {
    List<Department> deptList = Collections.singletonList(DEPARTMENT);
    when(departmentRepository.findAll()).thenReturn(deptList);
    List<Department> res = departmentService.findAll();
    Assert.assertEquals(deptList.size(), res.size());
  }
}
