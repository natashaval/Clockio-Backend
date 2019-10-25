package com.future.clockio.entity.company;

import com.future.clockio.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Photo")
public class Photo extends BaseEntity {
  @Id
  private String publicId;

  private String url;
  private boolean mainPhoto;

  @ManyToOne
  private Employee employee;
}
