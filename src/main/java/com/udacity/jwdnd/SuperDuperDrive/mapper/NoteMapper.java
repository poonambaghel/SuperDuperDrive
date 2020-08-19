package com.udacity.jwdnd.SuperDuperDrive.mapper;

import com.udacity.jwdnd.SuperDuperDrive.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES")
    List<Notes> getAllNotes();

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Notes getNoteById(Integer noteid);

    @Insert("INSERT INTO NOTES(notetitle, notedescription, userid) VALUES (" +
            "#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Notes note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    void delete(Integer noteid);

    @Delete("DELETE FROM NOTES")
    void deleteAll();

    @Update("UPDATE notes " +
            "SET notetitle = #{notetitle}, notedescription = #{notedescription} " +
            "WHERE noteid = #{noteid}")
    int update(String notetitle, String notedescription, Integer noteid);
}
