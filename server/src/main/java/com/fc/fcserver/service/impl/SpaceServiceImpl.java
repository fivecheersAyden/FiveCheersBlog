package com.fc.fcserver.service.impl;

import com.fc.fcserver.dao.SpaceMapper;
import com.fc.fcserver.dto.Space;
import com.fc.fcserver.entity.V2SpaceSt;
import com.fc.fcserver.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SpaceServiceImpl implements SpaceService {
    @Autowired
    SpaceMapper sm;

    @Override
    public boolean insertOne(String spaceContent, String spaceImgs, String spaceFiles) {
        return sm.insertOne(spaceContent, spaceImgs, spaceFiles);
    }

    @Override
    public boolean deleteOne(int spaceId) {
        return sm.deleteOne(spaceId);
    }

    @Override
    public boolean updateOne(Space newSpace) {
        V2SpaceSt v2NewSpace = new V2SpaceSt();
        v2NewSpace.setSpaceId(newSpace.getSpaceId());
        v2NewSpace.setSpaceTime(newSpace.getSpaceTime());
        v2NewSpace.setSpaceContent(newSpace.getSpaceContent());
        String imgs = String.join(",", newSpace.getSpaceImgs());
        v2NewSpace.setSpaceImgs(imgs);
        String files = String.join(",", newSpace.getSpaceFiles());
        v2NewSpace.setSpaceFiles(files);
        v2NewSpace.setLikeNum(newSpace.getLikeNum());
        return sm.updateOne(v2NewSpace);
    }

    @Override
    public List<Space> selectSpace(int startIndex, int length) {
        List<V2SpaceSt> v2SpaceStList = sm.selectSpace(startIndex, length);
        List<Space> spaceList = new ArrayList<>();
        for (V2SpaceSt v2Space:
             v2SpaceStList) {
            Space space = new Space();
            space.setSpaceId(v2Space.getSpaceId());
            space.setSpaceTime(v2Space.getSpaceTime());
            space.setSpaceContent(v2Space.getSpaceContent());
            List<String> imgsList = Arrays.asList(v2Space.getSpaceImgs().split(","));
            space.setSpaceImgs(imgsList);
            List<String> filesList = Arrays.asList(v2Space.getSpaceFiles().split(","));
            space.setSpaceFiles(filesList);
            space.setLikeNum(v2Space.getLikeNum());
            spaceList.add(space);
        }
        return spaceList;
    }

    @Override
    public boolean likeSpace(int spaceId) {
        return sm.likeSpace(spaceId);
    }
}
