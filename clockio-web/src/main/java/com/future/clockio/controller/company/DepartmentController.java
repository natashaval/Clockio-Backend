package com.future.clockio.controller.company;

import com.future.clockio.entity.company.Department;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.company.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/departments")
public class DepartmentController {
  private DepartmentService departmentService;

  @Autowired
  public DepartmentController(DepartmentService DepartmentService) {
    this.departmentService = DepartmentService;
  }

  @GetMapping
  public List<Department> getDepartments() {return departmentService.findAll();}

  @GetMapping(value = "/{id}")
  public Department getDepartment(@PathVariable("id") UUID id) { return departmentService.findById(id);}

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BaseResponse createDepartment(@RequestBody Department department) {
    return departmentService.createDepartment(department);
  }

  @PutMapping(value = "/{id}")
  public BaseResponse updateDepartment(@PathVariable("id") UUID id,
                                       @RequestBody Department department) {
    return departmentService.updateDepartment(id, department);
  }

  @DeleteMapping(value = "/{id}")
  public BaseResponse deleteDepartment(@PathVariable("id") UUID id) {
    return departmentService.deleteById(id);
  }
}
