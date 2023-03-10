package com.mynotes.mynotes.interfaces.JPA;

import com.mynotes.mynotes.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsersRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmailAndAuth (String email, String Auth);

    UserEntity findByAuth (String auth);

    UserEntity findByAuthAndId (String auth, Long id);

    Boolean existsByEmailAndAuth (String email, String Auth);

}
