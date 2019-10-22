package com.future.clockio.entity.company;

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
public class Photo {
  @Id
  private String publicId;

  private String url;
  private boolean mainPhoto;

  @ManyToOne
  private Employee employee;
}
