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
public class Contact {
  private String name;
  private String phone;
}
