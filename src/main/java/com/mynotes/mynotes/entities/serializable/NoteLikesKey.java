package com.mynotes.mynotes.entities.serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class NoteLikesKey implements Serializable {

    @Getter
    @Setter
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Getter
    @Setter
    @Column(name = "note_id", nullable = false)
    private Long noteId;

    public NoteLikesKey(Long userId, Long noteId) {
        this.userId = userId;
        this.noteId = noteId;
    }

    public NoteLikesKey() {
    }
}
