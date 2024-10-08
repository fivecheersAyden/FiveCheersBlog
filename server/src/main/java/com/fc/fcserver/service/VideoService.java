package com.fc.fcserver.service;

import com.fc.fcserver.dto.Space;
import com.fc.fcserver.entity.V2VideoSt;

import java.util.List;

public interface VideoService {
    boolean insertOne(String videoContent, String videoUrl);

    boolean deleteOne(int videoId);

    List<V2VideoSt> selectVideo(int startIndex, int length);
}
