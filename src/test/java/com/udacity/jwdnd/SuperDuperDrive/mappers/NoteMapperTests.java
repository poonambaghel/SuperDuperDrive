package com.udacity.jwdnd.SuperDuperDrive.mappers;

import com.udacity.jwdnd.SuperDuperDrive.model.Notes;
import com.udacity.jwdnd.SuperDuperDrive.mapper.NoteMapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@MybatisTest
public class NoteMapperTests {

    private Logger logger = LoggerFactory.getLogger(NoteMapperTests.class);

    @Autowired
    private NoteMapper noteMapper;

    @Test
    public void insertNoteWithNonexistUser() {

        Notes newNote = new Notes(
                null, "Hello World", "Hello", 1000);

        Assertions.assertThrows(Exception.class, () -> {
            this.noteMapper.insert(newNote);
        });
    }

    @Test
    public void insertNote() {

        Notes newNote = new Notes(null, "Hello World", "Hello");

        this.noteMapper.insert(newNote);

        Notes savedNote = this.noteMapper.getNoteById(newNote.getNoteid());

        Assertions.assertNotNull(savedNote);
        Assertions.assertEquals(newNote.getNotetitle(), savedNote.getNotetitle());
    }

    @Test
    public void findAllNotes() {

        Notes newNote = new Notes(null, "Hello World", "Hello");

        this.noteMapper.insert(newNote);

        List<Notes> notes = this.noteMapper.getAllNotes();

        Assertions.assertFalse(notes.isEmpty());
        Assertions.assertEquals(1, notes.size());
    }

    @Test
    public void deleteNote() {

        Notes newNote = new Notes(null, "Hello World", "Hello");

        this.noteMapper.insert(newNote);

        Integer noteId = newNote.getNoteid();

        Assertions.assertNotNull(noteId);

        this.noteMapper.delete(noteId);

        Notes note = this.noteMapper.getNoteById(noteId);

        Assertions.assertNull(note);
    }

    @Test
    public void deleteAllNotes() {

        Notes newNote = new Notes(null, "helloWorld", "hello");

        this.noteMapper.insert(newNote);

        List<Notes> notes = this.noteMapper.getAllNotes();

        Assertions.assertFalse(notes.isEmpty());

        this.noteMapper.deleteAll();

        notes = this.noteMapper.getAllNotes();

        Assertions.assertTrue(notes.isEmpty());
    }

    @Test
    public void updateNote() {

        Notes newNote = new Notes(null, "helloWorld", "hello");

        this.noteMapper.insert(newNote);

        Integer noteId = newNote.getNoteid();

        Assertions.assertNotNull(noteId);

        this.noteMapper.update("Hello World", "This is a test", noteId);

        Notes note = this.noteMapper.getNoteById(noteId);

        Assertions.assertNotNull(note);

        Assertions.assertEquals("Hello World", note.getNotetitle());
        Assertions.assertEquals("This is a test", note.getNotedescription());
    }
}
