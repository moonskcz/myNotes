package com.mynotes.mynotes.controllers;

import com.mynotes.mynotes.dtos.request.*;
import com.mynotes.mynotes.dtos.response.*;
import com.mynotes.mynotes.entities.NoteEntity;
import com.mynotes.mynotes.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class NotesController {

    @Autowired
    NoteService notesService;

    @GetMapping("/notes/all/{page}/{count}")
    public PublicNotesDTO getPublicNotes (@PathVariable("page") Integer page, @PathVariable("count") Integer count) {
        List<NoteEntity> notes = notesService.getPublicNotes(page, count);

        PublicNotesDTO publicNotesDTO = new PublicNotesDTO();
        publicNotesDTO.notes = new ArrayList<NoteDTO>();
        for (NoteEntity note: notes) {
            publicNotesDTO.notes.add(new NoteDTO(note.getId(), note.getContent()));
        }

        return publicNotesDTO;
    }

    @PostMapping("/notes")
    public NoteDTO getNote (@RequestBody GetNoteDTO getNoteDTO) {
        NoteEntity noteEntity = notesService.getNote(getNoteDTO);

        NoteDTO noteDTO = new NoteDTO();
        noteDTO.content = noteEntity.getContent();
        noteDTO.id = noteEntity.getId();
        return noteDTO;
    }

    @PostMapping("/notes/all")
    public UserNotesDTO getUserNotes (@RequestBody GetUserNotesDTO getUserNotesDTO) {
        List<NoteEntity> notes = notesService.findUserNotes(getUserNotesDTO);

        UserNotesDTO userNotesDTO = new UserNotesDTO();
        userNotesDTO.notes = new ArrayList<NoteDTO>();
        for (NoteEntity note: notes) {
            userNotesDTO.notes.add(new NoteDTO(note.getId(), note.getContent()));
        }

        return userNotesDTO;

    }

    @PutMapping("/notes")
    public NoteCreatedDTO createNote (@RequestBody CreateNoteDTO createNoteDTO) {
        NoteEntity noteEntity = notesService.createNote(createNoteDTO);
        NoteCreatedDTO noteCreatedDTO = new NoteCreatedDTO();
        noteCreatedDTO.id = noteEntity.getId();
        noteCreatedDTO.succesful = true;
        return noteCreatedDTO;

    }

    @DeleteMapping("/notes")
    public NoteDeletedDTO deleteNote (@RequestBody DeleteNoteDTO deleteNoteDTO) {
        Boolean deleted = notesService.deleteNote(deleteNoteDTO);

        NoteDeletedDTO noteDeletedDTO = new NoteDeletedDTO();
        noteDeletedDTO.deleted = deleted;
        return noteDeletedDTO;
    }

    @PatchMapping("/notes")
    public NoteEditedDTO editNote (@RequestBody EditNoteDTO editNoteDTO) {
        Boolean edited = notesService.editNote(editNoteDTO);

        NoteEditedDTO noteEditedDTO = new NoteEditedDTO();

        noteEditedDTO.succesful = edited;

        return noteEditedDTO;
    }

}
