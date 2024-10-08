package com.fc.fcserver.entity;

import lombok.Data;

@Data
public class V2CountSt {

  private long countId;
  private java.sql.Timestamp countTime;
  private long countNum;
  private String countDetail;
  private String countType;

}
