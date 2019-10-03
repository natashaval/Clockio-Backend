package com.future.clockio.controller.company;

import com.future.clockio.entity.company.Branch;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.company.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/branches")
public class BranchController {

  private BranchService branchService;

  @Autowired
  public BranchController(BranchService branchService) {
    this.branchService = branchService;
  }

  @GetMapping
  public List<Branch> getBranches() {return branchService.findAll();}

  @GetMapping(value = "/{id}")
  public Branch getBranch(@PathVariable("id") String id) { return branchService.findById(id);}

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BaseResponse createBranch(@RequestBody Branch branch) {
    return branchService.createBranch(branch);
  }

  @PutMapping(value = "/{id}")
  public BaseResponse updateBranch(@PathVariable("id") String id, @RequestBody Branch branch) {
    return branchService.updateBranch(id, branch);
  }

  @DeleteMapping(value = "/{id}")
  public BaseResponse deleteBranch(@PathVariable("id") String id) {
    return branchService.deleteById(id);
  }
}
