package com.mynotes.mynotes.entities;

import com.mynotes.mynotes.entities.serializable.NoteLikesKey;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "likes")
public class NoteLikeEntity {

    @Getter
    @Setter
    @EmbeddedId
    private NoteLikesKey noteLikesKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        NoteLikeEntity that = (NoteLikeEntity) o;
        return getNoteLikesKey() != null && Objects.equals(getNoteLikesKey(), that.getNoteLikesKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(noteLikesKey);
    }
}
