package com.fc.fcserver.controller;

import com.fc.fcserver.dto.Space;
import com.fc.fcserver.entity.V2VideoSt;
import com.fc.fcserver.service.VideoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    VideoService vs;

    //增加一条
    @PostMapping("/insert")
    public boolean insertVideo(@Param("videoContent") String videoContent, @Param("videoUrl") String videoUrl){
        return vs.insertOne(videoContent, videoUrl);
    }

    //删除一条
    @PostMapping("/delete")
    public boolean deleteVideo(@Param("videoId") int videoId){
        return vs.deleteOne(videoId);
    }

    //分页查询
    @GetMapping("/select")
    public List<V2VideoSt> selectVideo(@Param("startIndex") int startIndex, @Param("length") int length){
        return vs.selectVideo(startIndex, length);
    }
}
