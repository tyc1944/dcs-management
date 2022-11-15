package com.yunmo.dcs.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/** @author lh */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Location {
  private Double lng;
  private Double lat;
  private String detail;
}
