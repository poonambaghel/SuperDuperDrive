package com.udacity.jwdnd.SuperDuperDrive.service;

import com.udacity.jwdnd.SuperDuperDrive.mapper.FileMapper;
import com.udacity.jwdnd.SuperDuperDrive.mapper.UserFileMapper;
import com.udacity.jwdnd.SuperDuperDrive.mapper.UserMapper;
import com.udacity.jwdnd.SuperDuperDrive.model.Files;
import com.udacity.jwdnd.SuperDuperDrive.model.User;
import com.udacity.jwdnd.SuperDuperDrive.model.UserFiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileService {

    private Logger logger = LoggerFactory.getLogger(FileService.class);

    private FileMapper fileMapper;
    private UserFileMapper userFileMapper;
    private UserMapper userMapper;

    public FileService(
            FileMapper fileMapper,
            UserFileMapper userFileMapper,
            UserMapper userMapper) {

        this.fileMapper = fileMapper;
        this.userFileMapper = userFileMapper;
        this.userMapper = userMapper;
    }

    public Boolean isFileNameAvailableForUser(String username, String filename) {

        Map<String, Object> paraMap = new HashMap<>();

        paraMap.put("username", username);
        paraMap.put("filename", filename);

        return this.userFileMapper.getFileByUsernameAndFileName(paraMap).isEmpty();
    }

    public List<UserFiles> getFilesByUser(String username) {
        return this.userFileMapper.getFileByUsername(username);
    }

    public Boolean saveFile(MultipartFile file, String username) throws IOException {

        User user = this.userMapper.getUserByUsername(username);

        Integer userId = user.getUserid();

        byte[] fileData = file.getBytes();
        String contentType = file.getContentType();
        String fileSize = String.valueOf(file.getSize());
        String fileName = file.getOriginalFilename();

        this.fileMapper.insert(new Files(null, fileName, contentType, fileSize, userId, fileData));

        return true;
    }

    public Boolean deleteFile(Integer fileId) {

        this.fileMapper.delete(fileId);

        return true;
    }

    public Files getFileByFileId(Integer fileId) {
        return this.fileMapper.getFileById(fileId);
    }
}