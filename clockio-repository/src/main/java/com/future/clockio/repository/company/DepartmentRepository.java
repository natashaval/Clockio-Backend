package com.future.clockio.repository.company;

import com.future.clockio.entity.company.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, String> {
}
