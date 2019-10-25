package com.future.clockio.service.company;

import com.future.clockio.entity.company.Branch;
import com.future.clockio.response.base.BaseResponse;

import java.util.List;
import java.util.UUID;

public interface BranchService {
  Branch findById(UUID id);

  BaseResponse deleteById(UUID id);

  BaseResponse createBranch(Branch branch);

  BaseResponse updateBranch(UUID id, Branch branch);

  List<Branch> findAll();
}
