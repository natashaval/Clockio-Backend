package com.future.clockio.repository.company;

import com.future.clockio.entity.company.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
}
