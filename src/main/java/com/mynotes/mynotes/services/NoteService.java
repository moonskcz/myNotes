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
        userService.validateUser(createNoteDTO.user_id, createNoteDTO.auth);

        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setUser(createNoteDTO.user_id);
        noteEntity.setContent(createNoteDTO.content);
        notesRepository.save(noteEntity);

        return noteEntity;

    }

    public NoteEntity getNote(GetNoteDTO getNoteDTO) {
        userService.validateUser(getNoteDTO.user_id, getNoteDTO.auth);

        NoteEntity noteEntity = notesRepository.findByIdAndUser(getNoteDTO.note_id, getNoteDTO.user_id);

        return noteEntity;

    }

    public List<NoteEntity> findAllUserNotes(GetAllUserNotesDTO getAllUserNotesDTO) {
        userService.validateUser(getAllUserNotesDTO.user_id, getAllUserNotesDTO.auth);

        List<NoteEntity> noteEntity = notesRepository.findAllByUser(getAllUserNotesDTO.user_id);

        return noteEntity;
    }

    public Boolean deleteNote(DeleteNoteDTO deleteNoteDTO) {
        userService.validateUser(deleteNoteDTO.user_id, deleteNoteDTO.auth);

        try {
            notesRepository.deleteById(deleteNoteDTO.note_id);
        } catch (Exception e) {
            throw new FailedToDeleteNoteException();
        }

        return true;
    }

    @Transactional
    public Boolean editNote(EditNoteDTO editNoteDTO) {
        userService.validateUser(editNoteDTO.user_id, editNoteDTO.auth);

        try {
            NoteEntity noteEntity = notesRepository.findByIdAndUser(editNoteDTO.note_id, editNoteDTO.user_id);
            noteEntity.setContent(editNoteDTO.newContent);
            notesRepository.save(noteEntity);
        } catch (Exception e) {
            throw new FailedToUpdateNoteException();
        }

        return true;
    }
}
