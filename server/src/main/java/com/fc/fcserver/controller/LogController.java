package com.fc.fcserver.controller;

import com.fc.fcserver.service.LogService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    LogService ls;

    @PostMapping("/postNewLog")
    public boolean postNewLog(@Param("logOp") String logOp,@Param("logUUID") String logUUID, @Param("logIpAddress") String logIpAddress){
        try{
            return ls.postNewLog(logOp,logUUID,logIpAddress);
        }catch (Exception e){
            return false;
        }
    }
}
