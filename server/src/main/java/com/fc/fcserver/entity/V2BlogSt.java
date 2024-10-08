package com.fc.fcserver.entity;

import lombok.Data;

@Data
public class V2BlogSt {

  private String blogKey;
  private String title;
  private java.sql.Timestamp createdTime;
  private String content;

}
