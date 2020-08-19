package com.udacity.jwdnd.SuperDuperDrive.mapper;

import com.udacity.jwdnd.SuperDuperDrive.model.UserNote;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface UserNoteMapper {

    List<UserNote> getNotesByUsername(String username);

    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insertNoteByUsername(
            String username,
            String notetitle,
            String notedescription);
}
