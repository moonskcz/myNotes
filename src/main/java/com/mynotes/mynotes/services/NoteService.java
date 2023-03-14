package com.mynotes.mynotes.services;

import com.mynotes.mynotes.dtos.request.*;
import com.mynotes.mynotes.entities.NoteEntity;
import com.mynotes.mynotes.exceptions.FailedToDeleteNoteException;
import com.mynotes.mynotes.exceptions.FailedToUpdateNoteException;
import com.mynotes.mynotes.interfaces.JPA.INotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class NoteService {

    @Autowired
    INotesRepository notesRepository;

    @Autowired
    UserService userService;

    public NoteEntity createNote(CreateNoteDTO createNoteDTO) {
        userService.validateUser(createNoteDTO.userId, createNoteDTO.auth);

        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setUser(createNoteDTO.userId);
        noteEntity.setContent(createNoteDTO.content);
        notesRepository.save(noteEntity);

        return noteEntity;

    }

    public NoteEntity getNote(GetNoteDTO getNoteDTO) {
        userService.validateUser(getNoteDTO.userId, getNoteDTO.auth);

        NoteEntity noteEntity = notesRepository.findByIdAndUser(getNoteDTO.noteId, getNoteDTO.userId);

        return noteEntity;

    }

    public List<NoteEntity> findAllUserNotes(GetAllUserNotesDTO getAllUserNotesDTO) {
        userService.validateUser(getAllUserNotesDTO.userId, getAllUserNotesDTO.auth);

        List<NoteEntity> noteEntity = notesRepository.findAllByUser(getAllUserNotesDTO.userId);

        return noteEntity;
    }

    public Boolean deleteNote(DeleteNoteDTO deleteNoteDTO) {
        userService.validateUser(deleteNoteDTO.userId, deleteNoteDTO.auth);

        try {
            notesRepository.deleteById(deleteNoteDTO.noteId);
        } catch (Exception e) {
            throw new FailedToDeleteNoteException();
        }

        return true;
    }

    @Transactional
    public Boolean editNote(EditNoteDTO editNoteDTO) {
        userService.validateUser(editNoteDTO.userId, editNoteDTO.auth);

        try {
            NoteEntity noteEntity = notesRepository.findByIdAndUser(editNoteDTO.noteId, editNoteDTO.userId);
            noteEntity.setContent(editNoteDTO.newContent);
            notesRepository.save(noteEntity);
        } catch (Exception e) {
            throw new FailedToUpdateNoteException();
        }

        return true;
    }
}
