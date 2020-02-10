package com.future.clockio.service.impl.company;

import com.future.clockio.entity.company.Branch;
import com.future.clockio.exception.DataNotFoundException;
import com.future.clockio.exception.InvalidRequestException;
import com.future.clockio.repository.company.BranchRepository;
import com.future.clockio.response.base.BaseResponse;
import com.future.clockio.service.company.BranchService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static com.future.clockio.service.impl.helper.EntityMock.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BranchServiceTest {
  @Mock
  private BranchRepository branchRepository;

  @InjectMocks
  private BranchServiceImpl branchService;


  private Optional<Branch> branchOpt;

  @Before
  public void setUp() {
    branchOpt = Optional.of(BRANCH);
  }

  @Test
  public void findById_success() {
    when(branchRepository.findById(any(UUID.class))).thenReturn(branchOpt);
    Branch res = branchService.findById(BRANCH_ID);
    Assert.assertEquals(BRANCH_ID, res.getId());
    Assert.assertEquals(BRANCH_NAME, res.getName());
    Assert.assertEquals(DOUBLE_RANDOM, res.getLatitude(), 0.01);
    Assert.assertEquals(DOUBLE_RANDOM, res.getLongitude(), 0.01);
  }

  @Test
  public void findById_failed() {
    try {
      branchService.findById(BRANCH_ID);
    } catch (DataNotFoundException e) {
      Assert.assertEquals("Branch not found!", e.getMessage());
    }
  }

  @Test
  public void createBranch_success() {
    when(branchRepository.save(any(Branch.class))).thenReturn(BRANCH);
    BaseResponse res = branchService.createBranch(BRANCH);
    Assert.assertTrue(res.isSuccess());
    Assert.assertEquals("Branch is added!", res.getMessage());
  }

  @Test
  public void createBranch_failed() {
    try {
      branchService.createBranch(BRANCH);
    } catch (InvalidRequestException e) {
      Assert.assertEquals("Failed in create new branch!", e.getMessage());
    }
  }

  @Test
  public void findAll_success() {
    when(branchRepository.findAll()).thenReturn(Collections.singletonList(BRANCH));
    List<Branch> res = branchService.findAll();
    Assert.assertEquals(1, res.size());
  }

  @Test
  public void deleteBranch_idNotFound() {
    try {
      branchService.deleteById(BRANCH_ID);
    } catch (DataNotFoundException e) {
      Assert.assertEquals("Branch not found!", e.getMessage());
    }
  }

  @Test
  public void deleteById_success() {
    when(branchRepository.existsById(BRANCH_ID)).thenReturn(true);
    BaseResponse res = branchService.deleteById(BRANCH_ID);
    Assert.assertTrue(res.isSuccess());
    Assert.assertEquals("Branch is deleted!", res.getMessage());
  }

  @Test
  public void updateBranch_idNotFound(){
    try {
      branchService.updateBranch(BRANCH_ID, BRANCH);
    } catch (DataNotFoundException e) {
      Assert.assertEquals("Branch not found!", e.getMessage());
    }
  }

  @Test
  public void updateBranch_success() {
    when(branchRepository.findById(BRANCH_ID)).thenReturn(branchOpt);
    when(branchRepository.save(any(Branch.class))).thenReturn(BRANCH);
    BaseResponse res = branchService.updateBranch(BRANCH_ID, BRANCH);
    Assert.assertTrue(res.isSuccess());
    Assert.assertEquals("Branch is updated!", res.getMessage());
  }
}
