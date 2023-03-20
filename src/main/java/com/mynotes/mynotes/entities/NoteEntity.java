package com.mynotes.mynotes.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@EqualsAndHashCode
@Table(name = "notes")
public class NoteEntity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "userId")
    private Long user;

    @Getter
    @Setter
    @Column(name = "content")
    private String content;


    @Column(name = "is_public")
    private Boolean isPublic;

    @Getter
    @Setter
    @Column(name = "changed_at")
    private Date changedAt;

    @Getter
    @Setter
    @Column(name = "created_at")
    private Date createdAt;

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }
}
