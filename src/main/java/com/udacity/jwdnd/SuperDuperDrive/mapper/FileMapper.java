package com.udacity.jwdnd.SuperDuperDrive.mapper;

import com.udacity.jwdnd.SuperDuperDrive.model.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES(" +
            "filename, " +
            "contenttype, " +
            "filesize, " +
            "filedata, " +
            "userid) VALUES (" +
            "#{filename}, " +
            "#{contenttype}, " +
            "#{filesize}, " +
            "#{filedata}, " +
            "#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    int insert(Files file);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileid}")
    Files getFileById(Integer fileid);

    @Select("SELECT * FROM FILES")
    List<Files> getAllFiles();

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid}")
    void delete(Integer fileid);
}
