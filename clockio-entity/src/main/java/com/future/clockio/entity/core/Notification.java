package com.future.clockio.entity.core;

import com.future.clockio.entity.base.BaseEntity;
import com.future.clockio.entity.constant.DocumentName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = DocumentName.NOTIFICATION)
public class Notification extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String content;

  private double latitude;
  private double longitude;

  private Date startDate;

  private Date endDate;

//  private List<String> seenBy;
  // keep track of who has Seen Notification by employeeId;
}
