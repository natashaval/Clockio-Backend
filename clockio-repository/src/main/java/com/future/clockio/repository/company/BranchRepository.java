package com.future.clockio.repository.company;

import com.future.clockio.entity.company.Branch;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BranchRepository extends MongoRepository<Branch, String> {
}
