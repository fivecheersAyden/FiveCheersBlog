package com.fc.fcserver.entity;


import lombok.Data;

@Data
public class V2SpaceSt {

  private long spaceId;
  private java.sql.Timestamp spaceTime;
  private String spaceContent;
  private String spaceImgs;
  private String spaceFiles;
  private long likeNum;

}
