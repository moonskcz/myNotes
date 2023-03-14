package com.mynotes.mynotes.services;

import com.mynotes.mynotes.dtos.request.*;
import com.mynotes.mynotes.dtos.response.NoteDTO;
import com.mynotes.mynotes.entities.NoteEntity;
import com.mynotes.mynotes.exceptions.FailedToDeleteNoteException;
import com.mynotes.mynotes.exceptions.FailedToFindNoteException;
import com.mynotes.mynotes.exceptions.FailedToUpdateNoteException;
import com.mynotes.mynotes.exceptions.InvalidSearchParametersException;
import com.mynotes.mynotes.interfaces.JPA.INotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Properties;

@Service
public class NoteService {

    @Autowired
    INotesRepository notesRepository;

    @Autowired
    UserService userService;

    @Autowired
    Properties properties;

    public NoteEntity createNote(CreateNoteDTO createNoteDTO) {
        userService.validateUser(createNoteDTO.userId, createNoteDTO.auth);

        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setUser(createNoteDTO.userId);
        noteEntity.setContent(createNoteDTO.content);
        noteEntity.setPublic(createNoteDTO.isPublic);

        notesRepository.save(noteEntity);

        return noteEntity;

    }

    public NoteEntity getNote(GetNoteDTO getNoteDTO) {
        userService.validateUser(getNoteDTO.userId, getNoteDTO.auth);

        if (getNoteDTO.noteId == null) {
            throw new FailedToFindNoteException();
        }

        NoteEntity noteEntity = notesRepository.findByIdAndUser(getNoteDTO.noteId, getNoteDTO.userId);

        return noteEntity;

    }

    public List<NoteEntity> findUserNotes(GetUserNotesDTO getUserNotesDTO) {
        if (getUserNotesDTO.count < 1 && getUserNotesDTO.count > Integer.parseInt(properties.getProperty("Notes.NoteService.maxCount"))) {
            throw new InvalidSearchParametersException();
        }

        userService.validateUser(getUserNotesDTO.userId, getUserNotesDTO.auth);

        List<NoteEntity> noteEntity = notesRepository.findAllByUser(getUserNotesDTO.userId, PageRequest.of(getUserNotesDTO.offset, getUserNotesDTO.count));

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

        NoteEntity noteEntity;
        try {
            noteEntity = notesRepository.findByIdAndUser(editNoteDTO.noteId, editNoteDTO.userId);
        } catch (Exception e) {
            throw new FailedToUpdateNoteException();
        }

        if (editNoteDTO.newContent == null && editNoteDTO.setPublic != null) {
            noteEntity.setPublic(editNoteDTO.setPublic);
        } else if (editNoteDTO.newContent != null && editNoteDTO.setPublic == null) {
            noteEntity.setContent(editNoteDTO.newContent);
        } else {
            noteEntity.setContent(editNoteDTO.newContent);
            noteEntity.setPublic(editNoteDTO.setPublic);
        }

        try {
            notesRepository.save(noteEntity);
        } catch (Exception e) {
            throw new FailedToUpdateNoteException();
        }

        return true;
    }

    public List<NoteEntity> getPublicNotes(Integer page, Integer count) {
        if (count < 1 && count > Integer.parseInt(properties.getProperty("Notes.NoteService.maxCount"))) {
            throw new InvalidSearchParametersException();
        }

        return notesRepository.findAllByIsPublic(true, PageRequest.of(page, count));
    }
}
