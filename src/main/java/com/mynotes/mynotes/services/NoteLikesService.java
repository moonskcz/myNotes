package com.mynotes.mynotes.services;

import com.mynotes.mynotes.dtos.request.DeleteNoteLikeDTO;
import com.mynotes.mynotes.dtos.request.LikeNoteDTO;
import com.mynotes.mynotes.entities.NoteEntity;
import com.mynotes.mynotes.entities.NoteLikeEntity;
import com.mynotes.mynotes.entities.serializable.NoteLikesKey;
import com.mynotes.mynotes.exceptions.FailedToDeleteNoteLikeException;
import com.mynotes.mynotes.exceptions.FailedToFindNoteException;
import com.mynotes.mynotes.interfaces.JPA.INoteLikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class NoteLikesService {

    @Autowired
    UserService userService;

    @Autowired
    NoteService noteService;

    @Autowired
    INoteLikesRepository noteLikesRepository;


    @Transactional
    public NoteLikeEntity createNoteLike(LikeNoteDTO likeNoteDTO) {
        validateNoteAndUser(noteAndUserValidationStrategy.VALIDATE_ID_VALUE, likeNoteDTO.userId, likeNoteDTO.auth, likeNoteDTO.noteId);

        NoteLikeEntity noteLikeEntity = new NoteLikeEntity();
        NoteLikesKey noteLikesKey = new NoteLikesKey(likeNoteDTO.userId, likeNoteDTO.noteId);
        noteLikeEntity.setNoteLikesKey(noteLikesKey);
        noteLikesRepository.save(noteLikeEntity);
        return noteLikeEntity;
    }

    @Transactional
    public void deleteNoteLike(DeleteNoteLikeDTO deleteNoteLikeDTO) {
        validateNoteAndUser(noteAndUserValidationStrategy.VALIDATE_POST_EXISTENCE, deleteNoteLikeDTO.userId, deleteNoteLikeDTO.auth, deleteNoteLikeDTO.noteId);

        try {
            noteLikesRepository.deleteByNoteLikesKey(new NoteLikesKey(deleteNoteLikeDTO.userId, deleteNoteLikeDTO.noteId));
        } catch (RuntimeException e) {
            throw new FailedToDeleteNoteLikeException();
        }
    }

    private enum noteAndUserValidationStrategy {
        VALIDATE_ID_VALUE,
        VALIDATE_POST_EXISTENCE
    }

    private void validateNoteAndUser (noteAndUserValidationStrategy validationStrategy, Long userId, String auth, Long noteId) {
        userService.validateUser(userId, auth);

        switch (validationStrategy) {
            case VALIDATE_ID_VALUE:
                if (noteId == null || noteId < 1) {
                    throw new FailedToFindNoteException();
                }
            case VALIDATE_POST_EXISTENCE:
                NoteEntity noteEntity = noteService.notesRepository.findByIdAndUser(noteId, userId);
                if (noteEntity == null) {
                    throw new FailedToFindNoteException();
                }
        }
    }
}
