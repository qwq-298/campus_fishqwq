package com.compusfishqwq.compus_fishqwq.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
//import org.springframework.stereotype.RestController;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    // 上传目录（可在 application.properties 中配置）
   // 例如放在项目根目录下
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";


    /** 单张图片上传 */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> uploadImage(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        if (file.isEmpty()) {
            result.put("success", false);
            result.put("msg", "文件为空");
            return result;
        }

        try {
            // 创建目录
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) dir.mkdirs();

            // 使用 UUID 避免文件名冲突
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(dir, filename);

            // 保存文件
            file.transferTo(dest);

            // 返回访问 URL
            String fileUrl = "/uploads/" + filename;
            result.put("success", true);
            result.put("url", fileUrl);
            result.put("msg", "上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("msg", "上传失败：" + e.getMessage());
        }
        return result;
    }

    /** 多张图片上传 */
    @PostMapping(value = "/uploadMultiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> uploadMultipleImages(@RequestParam("files") MultipartFile[] files) {
        Map<String, Object> result = new HashMap<>();
        List<String> urls = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    File dir = new File(UPLOAD_DIR);
                    if (!dir.exists()) dir.mkdirs();

                    String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                    File dest = new File(dir, filename);
                    file.transferTo(dest);
                    urls.add("/uploads/" + filename);
                } catch (IOException e) {
                    e.printStackTrace();
                    result.put("success", false);
                    result.put("msg", "上传失败：" + e.getMessage());
                    return result;
                }
            }
        }

        result.put("success", true);
        result.put("urls", urls);
        result.put("msg", "上传成功");
        return result;
    }
}


