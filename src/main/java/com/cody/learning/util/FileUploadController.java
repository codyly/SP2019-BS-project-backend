package com.cody.learning.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.cody.learning.Constants;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    @PostMapping("/uploadToBase64")
    public String uploadToBase64(MultipartFile uploadFile, HttpServletRequest req) {
        String realPath = req.getSession().getServletContext().getRealPath("/uploadFile/");
        String base64="";
        try{
            base64 += FileToBase64.encodeBase64MF(uploadFile);
            return base64;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "failed";
    }

    @PostMapping("/upload")
    public String upload(MultipartFile uploadFile, HttpServletRequest req) {
        String realPath = req.getSession().getServletContext().getRealPath("/uploadFile/");
        String base64="";
        try{
            base64 += FileToBase64.encodeBase64MF(uploadFile);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(base64);
        String format = sdf.format(new Date());
        File folder = new File(realPath + format);
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }
        String oldName = uploadFile.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."), oldName.length());

        try {
            uploadFile.transferTo(new File(Constants.uploads_file_path, newName));
            return "upload successfully!";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "failed";

    }
}
