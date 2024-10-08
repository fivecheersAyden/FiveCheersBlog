package com.fc.fcserver.entity;

import lombok.Data;

@Data
public class V2VideoSt {

  private long videoId;
  private String videoUrl;
  private String videoContent;
  private java.sql.Timestamp time;


  public long getVideoId() {
    return videoId;
  }

  public void setVideoId(long videoId) {
    this.videoId = videoId;
  }


  public String getVideoUrl() {
    return videoUrl;
  }

  public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
  }


  public String getVideoContent() {
    return videoContent;
  }

  public void setVideoContent(String videoContent) {
    this.videoContent = videoContent;
  }


  public java.sql.Timestamp getTime() {
    return time;
  }

  public void setTime(java.sql.Timestamp time) {
    this.time = time;
  }

}
