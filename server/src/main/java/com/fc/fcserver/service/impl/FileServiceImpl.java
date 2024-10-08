package com.fc.fcserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fc.fcserver.service.FileService;
import com.fc.fcserver.service.thumbnailator.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ResourceLoader;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    ResourceLoader rl;

    @Value("${custom.file-path}" + "default/")
    private String uploadFilePath;

    @Value("${custom.file-path}" + "blog/")
    private String blogFilePath;

    // 默认新增文件
    @Override
    public String defaultUploadFile(MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            if (fileExtension.equals("png")||fileExtension.equals("jpg")){
                file = ImageUtil.compressImage(400 * 1024, file);
            }
            String newFilename = UUID.randomUUID().toString() + "." + fileExtension;
            // 指定绝对路径
            String saveDirectory = uploadFilePath + fileExtension + "/";
            Path savePath = Paths.get(saveDirectory, newFilename); // 使用 Paths.get 创建路径
            Files.createDirectories(savePath.getParent());
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, savePath, StandardCopyOption.REPLACE_EXISTING);
            }
            String baseUrl = "http://www.fivecheers.com/resource/" + "default/" + fileExtension + "/";
            if (fileExtension.equals("mp4")){
                //使用ffmpeg将file压缩为720p,savePsth下的文件压缩后同名文件放置在savePath/720p/下
                // 定义压缩后的视频存放路径
                String compressedDirectory = saveDirectory + "720p/";
                Path compressedSavePath = Paths.get(compressedDirectory, newFilename); // 使用 Paths.get 创建压缩视频的路径
                Files.createDirectories(compressedSavePath.getParent());
                // 构建ffmpeg命令来压缩视频
                String ffmpegCmd = "ffmpeg -i " + savePath.toString() + " -s hd720 -c:v libx264 -crf 23 -c:a aac -strict -2 " + compressedSavePath.toString();
                // 执行ffmpeg命令
                Process process = Runtime.getRuntime().exec(new String[] { "bash", "-c", ffmpegCmd });
                baseUrl = "http://www.fivecheers.com/resource/" + "default/" + fileExtension + "/720p/";
                //清空缓存区，保证exe执行
                new Thread(() -> {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            String fileUrl = baseUrl + newFilename;
            return fileUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    // 新增博客文件
    @Override
    public JSONObject uploadBlogFile(MultipartFile file) {
        JSONObject result = new JSONObject();
        try {
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            if (fileExtension.equals("png")||fileExtension.equals("jpg")){
                file = ImageUtil.compressImage(400 * 1024, file);
            }
            String newFilename = UUID.randomUUID().toString() + "." + fileExtension;
            // 指定绝对路径
            String saveDirectory = blogFilePath + fileExtension + "/";
            Path savePath = Paths.get(saveDirectory, newFilename); // 使用 Paths.get 创建路径
            Files.createDirectories(savePath.getParent());
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, savePath, StandardCopyOption.REPLACE_EXISTING);
            }
            String baseUrl = "http://www.fivecheers.com/resource/" + "blog/" + fileExtension + "/";
            if (fileExtension.equals("mp4")){
                //使用ffmpeg将file压缩为720p,savePsth下的文件压缩后同名文件放置在savePath/720p/下
                // 定义压缩后的视频存放路径
                String compressedDirectory = saveDirectory + "720p/";
                Path compressedSavePath = Paths.get(compressedDirectory, newFilename); // 使用 Paths.get 创建压缩视频的路径
                Files.createDirectories(compressedSavePath.getParent());
                // 构建ffmpeg命令来压缩视频
                String ffmpegCmd = "ffmpeg -i " + savePath.toString() + " -s hd720 -c:v libx264 -crf 23 -c:a aac -strict -2 " + compressedSavePath.toString();
                // 执行ffmpeg命令
                Process process = Runtime.getRuntime().exec(new String[] { "bash", "-c", ffmpegCmd });
                baseUrl = "http://www.fivecheers.com/resource/" + "blog/" + fileExtension + "/720p/";
                //清空缓存区，保证exe执行
                new Thread(() -> {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            String fileUrl = baseUrl + newFilename;
            JSONObject data = new JSONObject();
            data.put("errFiles", new String[]{});
            JSONObject succMap = new JSONObject();
            succMap.put(newFilename, fileUrl);
            data.put("succMap", succMap);

            result.put("msg", "");
            result.put("code", 0);
            result.put("data", data);
        } catch (Exception e) {
            result.put("msg", "error");
            result.put("code", 1);
            result.put("data", new JSONObject());
        }
        return result;
    }

}
