package com.udacity.jwdnd.SuperDuperDrive.mappers;

import com.udacity.jwdnd.SuperDuperDrive.model.Files;
import com.udacity.jwdnd.SuperDuperDrive.mapper.FileMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@MybatisTest
public class FileMapperTests {

    public final static String CONTENT_TYPE = "txt";
    public final static String FILE_NAME = "hello";

    private Logger logger = LoggerFactory.getLogger(FileMapperTests.class);

    @Autowired
    private FileMapper fileMapper;

    private Integer fileId;

    @Before
    public void before() {

        Files newFile = new Files(
                null,
                FILE_NAME,
                CONTENT_TYPE,
                "3GB",
                null
        );

        this.fileMapper.insert(newFile);

        this.fileId = newFile.getFileid();
    }

    @Test
    public void insertFile() {

        Assertions.assertNotNull(this.fileId);

        Files file = this.fileMapper.getFileById(this.fileId);

        Assertions.assertNotNull(file);
        Assertions.assertEquals(CONTENT_TYPE, file.getContenttype());
        Assertions.assertEquals(FILE_NAME, file.getFilename());
        Assertions.assertNull(file.getUserid());
    }

    @Test
    public void deleteFile() {

        Assertions.assertNotNull(this.fileId);

        this.fileMapper.delete(this.fileId);

        Files file = this.fileMapper.getFileById(this.fileId);

        Assertions.assertNull(file);
    }

    @Test
    public void getAllFiles() {

        List<Files> fileList = this.fileMapper.getAllFiles();

        Assertions.assertFalse(fileList.isEmpty());
        Assertions.assertTrue(fileList.size() == 1);
    }
}
