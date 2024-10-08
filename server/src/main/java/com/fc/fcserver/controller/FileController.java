package com.fc.fcserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.fc.fcserver.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fs;

    @PostMapping("/defaultUpLoadFile")    //上传文件并返回网址
    public String upLoadFile(@RequestParam("file") MultipartFile file){
        return fs.defaultUploadFile(file);
    }

    @PostMapping("/blogUpLoadFile")    //上传文件并返回网址
    public JSONObject upLoadBlogFile(@RequestParam("file[]") MultipartFile file){
        return fs.uploadBlogFile(file);
    }

}
