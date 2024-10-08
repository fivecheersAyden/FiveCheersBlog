package com.fc.fcserver.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String defaultUploadFile(MultipartFile file);

    JSONObject uploadBlogFile(MultipartFile file);
}
