package com.future.clockio.entity.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.future.clockio.entity.constant.DocumentName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Document(collection = DocumentName.DEPARTMENT)
@Entity
@Table(name = "Department")
public class Department {

  @Id
  private String id;

  private String name;

//  @ManyToOne(fetch = FetchType.LAZY)
//  private Branch branch;

//  @DBRef(lazy = true)
//  private Branch branch;

  private String branchId;
}
