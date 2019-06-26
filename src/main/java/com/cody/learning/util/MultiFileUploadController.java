package com.cody.learning.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.cody.learning.Constants;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.context.annotation.Configuration;

@RestController
public class MultiFileUploadController {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    @PostMapping("/uploads")
    public String upload(MultipartFile[] uploadFiles, HttpServletRequest req) {
        for (int i = 0; i < uploadFiles.length; i++) {
            MultipartFile uploadFile = uploadFiles[i];
            String realPath = req.getSession().getServletContext().getRealPath("/uploadFile/");
            String format = sdf.format(new Date());
            File folder = new File(realPath + format);
            if (!folder.isDirectory()) {
                folder.mkdirs();
            }
            String oldName = uploadFile.getOriginalFilename();
            String newName = UUID.randomUUID().toString()
                    + oldName.substring(oldName.lastIndexOf("."), oldName.length());
            try {
                uploadFile.transferTo(new File(Constants.uploads_file_path, newName));
            } catch (IOException e) {
                e.printStackTrace();
                return "failed";
            }
        }
        return "success";

    }
}
