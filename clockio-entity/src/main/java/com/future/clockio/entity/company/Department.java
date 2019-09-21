package com.future.clockio.entity.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.future.clockio.entity.constant.DocumentName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = DocumentName.DEPARTMENT)
public class Department {

  @Id
  private String id;

  private String name;

//  @DBRef(lazy = true)
//  private Branch branch;

  private String branchId;
}
