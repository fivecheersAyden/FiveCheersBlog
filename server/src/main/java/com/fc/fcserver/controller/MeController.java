package com.fc.fcserver.controller;

import com.fc.fcserver.dto.MeList;
import com.fc.fcserver.service.MeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
public class MeController {

    @Autowired
    MeService ms;

    @GetMapping("/getList")
    public MeList getMyList(){
        return ms.getMyList();
    }

    @PostMapping("/changeKey")
    public boolean changeKey(@Param("type") String type, @Param("value") String value){
        return ms.changeKey(type, value);
    }
}
