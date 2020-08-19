package com.udacity.jwdnd.SuperDuperDrive.controller;

import com.udacity.jwdnd.SuperDuperDrive.model.UserNote;
import com.udacity.jwdnd.SuperDuperDrive.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private Logger logger = LoggerFactory.getLogger(NoteController.class);

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/note")
    public String noteSubmit(
            @ModelAttribute("userNoteVO") UserNote userNoteVO,
            Authentication authentication,
            Model model
    ) {

        String username = authentication.getPrincipal().toString();

        this.logger.error("Submitted Note: " + userNoteVO.toString());

        Boolean isSuccess =
                this.noteService.insertOrUpdateNoteByUser(username, userNoteVO);

        return "redirect:/result?isSuccess=" + isSuccess;
    }

    @GetMapping("/note")
    public String noteDeletion(
            @ModelAttribute("userNoteVO") UserNote userNoteVO,
            @RequestParam(required = false, name = "noteId") Integer noteId,
            Authentication authentication,
            Model model
    ) {
        String username = authentication.getPrincipal().toString();

        this.logger.error(noteId.toString());

        Boolean isSuccess = this.noteService.deleteNote(noteId, username);

        return "redirect:/result?isSuccess=" + isSuccess;
    }
}
