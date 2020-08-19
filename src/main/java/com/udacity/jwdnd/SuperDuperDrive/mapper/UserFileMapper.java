package com.udacity.jwdnd.SuperDuperDrive.mapper;

import com.udacity.jwdnd.SuperDuperDrive.model.UserFiles;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserFileMapper {

    List<UserFiles> getFileByUsername(String username);

    List<UserFiles> getFileByUsernameAndFileName(Map<String, Object> paraMap);
}
