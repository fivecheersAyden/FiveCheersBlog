package com.fc.fcserver.entity;

import lombok.Data;

@Data
public class V2MeSt {

  private String type;
  private String value;


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
