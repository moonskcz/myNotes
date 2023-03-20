package com.mynotes.mynotes.interfaces.JPA;

import com.mynotes.mynotes.entities.NoteLikeEntity;
import com.mynotes.mynotes.entities.serializable.NoteLikesKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INoteLikesRepository extends JpaRepository<NoteLikeEntity, Long> {

    void deleteByNoteLikesKey (NoteLikesKey noteLikesKey);

}
