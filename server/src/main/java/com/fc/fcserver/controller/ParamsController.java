package com.fc.fcserver.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("params")
public class ParamsController {

    @Value("${custom.manager-secret}")
    private String managerSecret;

    @PostMapping("checkManagerSecret")
    public boolean checkManagerSecret(@Param("password") String password){
        return managerSecret.equals(password);
    }
}
