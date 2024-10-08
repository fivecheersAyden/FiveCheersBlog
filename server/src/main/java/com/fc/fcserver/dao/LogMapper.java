package com.fc.fcserver.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {

    @Insert("INSERT INTO v2_log_st (log_op, log_uuid, log_ip_address) VALUES (#{logOp}, #{logUUID}, #{logIpAddress})")
    boolean insertNewPost(String logOp, String logUUID, String logIpAddress);
}
