package com.mynotes.mynotes.controllers;

import com.mynotes.mynotes.dtos.request.DeleteNoteLikeDTO;
import com.mynotes.mynotes.dtos.request.LikeNoteDTO;
import com.mynotes.mynotes.dtos.request.NoteLikeDTO;
import com.mynotes.mynotes.dtos.response.GenericSuccesDTO;
import com.mynotes.mynotes.entities.NoteLikeEntity;
import com.mynotes.mynotes.exceptions.FunctionNotAllowed;
import com.mynotes.mynotes.services.NoteLikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

@RestController
public class NoteLikesController {

    @Autowired
    NoteLikesService noteLikesService;

    @Autowired
    Properties properties;

    @PutMapping("/likes")
    public GenericSuccesDTO likeNote (@RequestBody LikeNoteDTO likeNoteDTO) {
        if (!"true".equals(properties.getProperty("Notes.allowPublicNotes"))){
            throw new FunctionNotAllowed();
        }


         NoteLikeEntity noteLikeEntity = noteLikesService.createNoteLike(likeNoteDTO);

         if (noteLikeEntity != null) {
             return new GenericSuccesDTO(true);
         } else {
             return new GenericSuccesDTO(false);
         }
    }

    @PostMapping("/likes")
    public NoteLikeDTO getAlUserNoteLikes () {
        if (!"true".equals(properties.getProperty("Notes.allowPublicNotes"))){
            throw new FunctionNotAllowed();
        }

        return new NoteLikeDTO();
        //TODO decide how to get user likes
    }

    @DeleteMapping("/likes")
    public GenericSuccesDTO deleteNoteLike (@RequestBody DeleteNoteLikeDTO deleteNoteLikeDTO) {
        if (!"true".equals(properties.getProperty("Notes.allowPublicNotes"))){
            throw new FunctionNotAllowed();
        }

        noteLikesService.deleteNoteLike(deleteNoteLikeDTO);

        return new GenericSuccesDTO(true);
    }

}
