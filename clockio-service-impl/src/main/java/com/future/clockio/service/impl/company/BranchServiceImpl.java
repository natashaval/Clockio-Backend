package com.future.clockio.service.impl.company;

import com.future.clockio.entity.company.Branch;
import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.exception.InvalidRequestException;
import com.future.clockio.repository.company.BranchRepository;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.company.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchServiceImpl implements BranchService {

  private BranchRepository branchRepository;

  @Autowired
  public BranchServiceImpl(BranchRepository branchRepository) {
    this.branchRepository = branchRepository;
  }

  @Override
  public Branch findById(String id) {
    return branchRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Branch not found!"));
  }

  @Override
  public BaseResponse deleteById(String id) {
    Branch exist = branchRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Branch not found!"));
    if (exist != null) {
      branchRepository.deleteById(id);
      return BaseResponse.success("Branch is deleted!");
    }
    else throw new InvalidRequestException("Failed in delete branch!");
  }

  @Override
  public BaseResponse createBranch(Branch branch) {
    Optional.of(branch)
            .map(branchRepository::save)
            .orElseThrow(() -> new InvalidRequestException("Failed in create new branch!"));
    return BaseResponse.success("Branch is added!");
  }

  @Override
  public BaseResponse updateBranch(String id, Branch branch) {
    Optional.of(id)
            .map(branchRepository::findById)
            .orElseThrow(() -> new DataNotFoundException("Branch not found!"))
            .map(existBranch -> this.copyProperties(branch, existBranch))
            .map(branchRepository::save)
            .orElse(branch);

    return BaseResponse.success("Branch is updated!");
  }

  @Override
  public List<Branch> findAll() {
    return branchRepository.findAll();
  }

  private Branch copyProperties(Branch branch, Branch targetBranch) {
    targetBranch.setName(branch.getName());
    targetBranch.setCountry(branch.getCountry());
    targetBranch.setCity(branch.getCity());
    targetBranch.setLocation(branch.getLocation());
    return targetBranch;
  }
}