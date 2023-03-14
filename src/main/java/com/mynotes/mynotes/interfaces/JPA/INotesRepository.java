package com.mynotes.mynotes.interfaces.JPA;

import com.mynotes.mynotes.entities.NoteEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface INotesRepository extends JpaRepository<NoteEntity, Long> {

    NoteEntity findByIdAndUser (Long id, Long userId);

    List<NoteEntity> findAllByUser (Long userId, Pageable pageable);

    List<NoteEntity> findAllByIsPublic (Boolean isPublic, Pageable pageable);

    /*@Transactional
    @Modifying
    @Query("update note n set n.content = ?1 where n.id = ?2")
    void updateContentById(String content, Long id);*/

}
