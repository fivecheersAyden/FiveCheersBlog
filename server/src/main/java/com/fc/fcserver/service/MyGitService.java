package com.fc.fcserver.service;

import com.fc.fcserver.entity.V2MygitSt;

import java.util.List;

public interface MyGitService {
    boolean insertOne(String gitContent, String gitVideo, String gitUrl, String runUrl);

    boolean deleteOne(int gitId);

    List<V2MygitSt> selectMyGit(int startIndex, int length);
}
