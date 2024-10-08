package com.fc.fcserver.service.impl;

import com.fc.fcserver.dao.VideoMapper;
import com.fc.fcserver.dto.Space;
import com.fc.fcserver.entity.V2VideoSt;
import com.fc.fcserver.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    VideoMapper vm;

    @Override
    public boolean insertOne(String videoContent, String videoUrl) {
        return vm.insertOne(videoContent, videoUrl);
    }

    @Override
    public boolean deleteOne(int videoId) {
        return vm.deleteOne(videoId);
    }

    @Override
    public List<V2VideoSt> selectVideo(int startIndex, int length) {
        return vm.selectVideos(startIndex, length);
    }
}
