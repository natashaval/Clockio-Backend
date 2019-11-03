package com.future.clockio.entity.company;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.future.clockio.entity.base.BaseEntity;
import com.future.clockio.entity.constant.DocumentName;
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
@Table(name = DocumentName.PHOTO)
@JsonIgnoreProperties({"hibernateLazyInitializer","employee"})
public class Photo extends BaseEntity {

  @Id
  private String publicId;

  private String url;
  private boolean mainPhoto;

  @ManyToOne
  private Employee employee;
}
