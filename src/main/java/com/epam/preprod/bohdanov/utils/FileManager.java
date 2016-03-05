package com.epam.preprod.bohdanov.utils;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class FileManager {
    private static final Logger LOG = Logger.getLogger(FileManager.class);
    private String uploadPath;
    private String appPath;

    public FileManager(String uploadPath, String appPath) {
        this.uploadPath = uploadPath;
        this.appPath = appPath;
    }

    public String loadImage(HttpServletRequest request) {
        String imagePath = null;
        String savePath = appPath + File.separator + uploadPath;

        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }

        try {
            for (Part part : request.getParts()) {
                String extension = extractFileExtension(part);
                if (!extension.isEmpty()) {
                    String fileName = generateFileName(savePath) + extension;
                    part.write(savePath + File.separator + fileName);
                    imagePath = uploadPath + File.separator + fileName;

                }
            }
        } catch (IOException | ServletException e) {
            LOG.error("File upload error " + e.getMessage());
        }

        return imagePath;
    }

    public String extractFileExtension(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (!StringUtils.isEmpty(s) && s.trim().startsWith("filename")) {
                return s.substring(s.lastIndexOf("."), s.length() - 1);
            }
        }
        return "";
    }

    public String generateFileName(String savePath) {
        String fileName;
        Random r = ThreadLocalRandom.current();
        do {
            fileName = String.valueOf(r.nextInt(Integer.MAX_VALUE));
        } while (new File(savePath + File.separator + fileName).exists());
        return fileName;
    }

    public void removeImage(String name) {
        if (!StringUtils.isEmpty(name))
            new File(appPath + File.separator + name).delete();
    }
}
