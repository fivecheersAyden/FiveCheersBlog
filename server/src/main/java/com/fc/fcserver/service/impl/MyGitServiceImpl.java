package com.fc.fcserver.service.impl;

import com.fc.fcserver.dao.MyGitMapper;
import com.fc.fcserver.entity.V2MygitSt;
import com.fc.fcserver.service.MyGitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyGitServiceImpl implements MyGitService {

    @Autowired
    MyGitMapper mm;

    @Override
    public boolean insertOne(String gitContent, String gitVideo, String gitUrl, String runUrl) {
        return mm.insertOne(gitContent, gitUrl, gitVideo, runUrl);
    }

    @Override
    public boolean deleteOne(int gitId) {
        return mm.deleteOne(gitId);
    }

    @Override
    public List<V2MygitSt> selectMyGit(int startIndex, int length) {
        return mm.selectMyGit(startIndex, length);
    }
}
