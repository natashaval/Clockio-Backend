package com.future.clockio.service.company;

import com.future.clockio.entity.company.Branch;
import com.future.clockio.response.base.BaseResponse;

import java.util.List;

public interface BranchService {
  Branch findById(String id);

  BaseResponse deleteById(String id);

  BaseResponse createBranch(Branch branch);

  BaseResponse updateBranch(String id, Branch branch);

  List<Branch> findAll();
}
