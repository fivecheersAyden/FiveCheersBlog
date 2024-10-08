package com.fc.fcserver.entity;

import lombok.Data;

@Data
public class V2AmideadSt {

  private long deadId;
  private long amIDead;
  private java.sql.Timestamp time;
  private String lastWord;
  private String mapUrl;
  private String giftUrl;
  private long giftStill;

}
