package com.future.clockio.entity.core;

import com.future.clockio.entity.base.BaseEntity;
import com.future.clockio.entity.base.Coordinate;
import com.future.clockio.entity.constant.DocumentName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = DocumentName.NOTIFICATION)
public class Notification extends BaseEntity {

  @Id
  private String id;

  private String title;

  private String content;

  private Coordinate coordinate;

  private Date startDate;

  private Date endDate;

  private List<String> seenBy;
  // keep track of who has Seen Notification by employeeId;
}
