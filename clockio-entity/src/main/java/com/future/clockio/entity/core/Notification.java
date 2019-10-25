package com.future.clockio.entity.core;

import com.future.clockio.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Document(collection = DocumentName.NOTIFICATION)
@Entity
@Table(name = "Notification")
public class Notification extends BaseEntity {

  @Id
  private String id;

  private String title;

  private String content;

  private double latitude;
  private double longitude;

  private Date startDate;

  private Date endDate;

//  private List<String> seenBy;
  // keep track of who has Seen Notification by employeeId;
}
