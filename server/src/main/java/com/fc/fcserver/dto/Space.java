package com.fc.fcserver.dto;

import lombok.Data;

import java.util.List;

@Data
public class Space {
    private long spaceId;
    private java.sql.Timestamp spaceTime;
    private String spaceContent;
    private List<String> spaceImgs;
    private List<String> spaceFiles;
    private long likeNum;
}
