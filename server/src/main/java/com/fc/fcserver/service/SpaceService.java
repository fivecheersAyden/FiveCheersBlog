package com.fc.fcserver.service;

import com.fc.fcserver.dto.Space;
import com.fc.fcserver.entity.V2SpaceSt;

import java.util.List;

public interface SpaceService {
    boolean insertOne(String spaceContent, String spaceImgs, String spaceFiles);

    boolean deleteOne(int spaceId);

    boolean updateOne(Space newSpace);

    List<Space> selectSpace(int startIndex, int length);

    boolean likeSpace(int spaceId);
}
