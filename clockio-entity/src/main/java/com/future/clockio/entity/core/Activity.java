package com.future.clockio.entity.core;


import com.future.clockio.entity.base.BaseEntity;
import com.future.clockio.entity.base.Location;
import com.future.clockio.entity.company.Employee;
import com.future.clockio.entity.constant.DocumentName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = DocumentName.ACTIVITY)
public class Activity extends BaseEntity {

  @Id
  private String Id;

  @DBRef(lazy = true)
  private Employee employee;

  private String title;

  private String content;

  private Date date;

  private String startTime;

  private String endTime;

  private Location location;
}
