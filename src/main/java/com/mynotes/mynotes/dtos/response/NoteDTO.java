package com.mynotes.mynotes.dtos.response;

public class NoteDTO {

    public Long id;

    public String content;

    public NoteDTO(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public NoteDTO() {
    }
}
