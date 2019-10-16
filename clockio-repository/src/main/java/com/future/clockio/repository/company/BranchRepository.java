package com.future.clockio.repository.company;

import com.future.clockio.entity.company.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, String> {
}
