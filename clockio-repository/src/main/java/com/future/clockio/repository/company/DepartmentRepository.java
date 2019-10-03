package com.future.clockio.repository.company;

import com.future.clockio.entity.company.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentRepository extends MongoRepository<Department, String> {
}
