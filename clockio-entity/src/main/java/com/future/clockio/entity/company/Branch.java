package com.future.clockio.entity.company;

import com.future.clockio.entity.base.BaseEntity;
import com.future.clockio.entity.constant.DocumentName;
import com.future.clockio.entity.base.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = DocumentName.BRANCH)
public class Branch extends BaseEntity {

  @Id
  private String id;

  private String name;

  private String city;

  private String country;

  private Coordinate coordinate;
}
