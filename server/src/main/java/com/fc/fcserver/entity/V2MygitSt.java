package com.fc.fcserver.entity;

import lombok.Data;

@Data
public class V2MygitSt {

  private long gitId;
  private String gitVideo;
  private String gitContent;
  private String gitUrl;
  private String runUrl;
  private java.sql.Timestamp time;


  public long getGitId() {
    return gitId;
  }

  public void setGitId(long gitId) {
    this.gitId = gitId;
  }


  public String getGitVideo() {
    return gitVideo;
  }

  public void setGitVideo(String gitVideo) {
    this.gitVideo = gitVideo;
  }


  public String getGitContent() {
    return gitContent;
  }

  public void setGitContent(String gitContent) {
    this.gitContent = gitContent;
  }


  public String getGitUrl() {
    return gitUrl;
  }

  public void setGitUrl(String gitUrl) {
    this.gitUrl = gitUrl;
  }


  public String getRunUrl() {
    return runUrl;
  }

  public void setRunUrl(String runUrl) {
    this.runUrl = runUrl;
  }


  public java.sql.Timestamp getTime() {
    return time;
  }

  public void setTime(java.sql.Timestamp time) {
    this.time = time;
  }

}
