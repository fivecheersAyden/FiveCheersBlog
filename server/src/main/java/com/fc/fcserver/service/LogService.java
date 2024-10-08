package com.fc.fcserver.service;

public interface LogService  {
    boolean postNewLog(String logOp, String logUUID, String logIpAddress);
}
