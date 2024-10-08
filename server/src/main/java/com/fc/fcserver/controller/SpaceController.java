package com.fc.fcserver.controller;

import com.fc.fcserver.dto.Space;
import com.fc.fcserver.entity.V2SpaceSt;
import com.fc.fcserver.service.SpaceService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/space")
public class SpaceController {
    @Autowired
    SpaceService sp;

    //增加一条
    @PostMapping("/insert")
    public boolean insertSpeace(@Param("spaceContent") String spaceContent, @Param("spaceImgs") String spaceImgs, @Param("spaceFiles") String spaceFiles){
        return sp.insertOne(spaceContent, spaceImgs, spaceFiles);
    }

    //删除一条
    @PostMapping("/delete")
    public boolean deleteSpace(@Param("spaceId") int spaceId){
        return sp.deleteOne(spaceId);
    }

    //编辑一条
    @PostMapping("/update")
    public boolean updateSpace(@RequestBody Space newSpace){
        return sp.updateOne(newSpace);
    }

    //分页查询
    @GetMapping("/select")
    public List<Space> selectSpace(@Param("startIndex") int startIndex, @Param("length") int length){
        return sp.selectSpace(startIndex, length);
    }

    //点赞
    @PostMapping("/like")
    public boolean likeSpace(@Param("spaceId") int spaceId){
        return sp.likeSpace(spaceId);
    }


}
