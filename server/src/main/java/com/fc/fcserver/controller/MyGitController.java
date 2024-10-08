package com.fc.fcserver.controller;

import com.fc.fcserver.entity.V2MygitSt;
import com.fc.fcserver.entity.V2VideoSt;
import com.fc.fcserver.service.MyGitService;
import com.fc.fcserver.service.VideoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mygit")
public class MyGitController {

    @Autowired
    MyGitService ms;

    //增加一条
    @PostMapping("/insert")
    public boolean insertMyGit(@Param("gitContent") String gitContent,
                               @Param("gitVideo") String gitVideo,
                               @Param("gitUrl") String gitUrl,
                               @Param("runUrl") String runUrl){
        return ms.insertOne(gitContent, gitVideo, gitUrl, runUrl);
    }

    //删除一条
    @PostMapping("/delete")
    public boolean deleteMyGit(@Param("gitId") int gitId){
        return ms.deleteOne(gitId);
    }

    //分页查询
    @GetMapping("/select")
    public List<V2MygitSt> selectMyGit(@Param("startIndex") int startIndex, @Param("length") int length){
        return ms.selectMyGit(startIndex, length);
    }

}
