package com.mynotes.mynotes.services;

import com.mynotes.mynotes.dtos.request.CreateUserDTO;
import com.mynotes.mynotes.dtos.request.LoginUserDTO;
import com.mynotes.mynotes.entities.UserEntity;
import com.mynotes.mynotes.exceptions.InvalidLoginException;
import com.mynotes.mynotes.exceptions.InvalidRegistrationException;
import com.mynotes.mynotes.exceptions.InvalidUserException;
import com.mynotes.mynotes.exceptions.UserAlreadyExistsException;
import com.mynotes.mynotes.interfaces.JPA.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private IUsersRepository usersRepository;

    @Transactional
    public UserEntity createUser(CreateUserDTO createUserDTO) {
        if (createUserDTO.email.isEmpty()) {
            throw new InvalidRegistrationException();
        }

        if (createUserDTO.name.isEmpty()) {
            throw new InvalidRegistrationException();
        }

        if (createUserDTO.auth.isEmpty()) {
            throw new InvalidRegistrationException();
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setAuth(createUserDTO.auth);
        userEntity.setName(createUserDTO.name);
        userEntity.setEmail(createUserDTO.email);
        try {
            usersRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException();
        }
        return userEntity;
    }

    public UserEntity loginUser(LoginUserDTO loginUserDTO) {
        if (loginUserDTO == null) {
            throw new InvalidLoginException();
        }
        if (loginUserDTO.email == null || loginUserDTO.email.isEmpty()) {
            throw new InvalidLoginException();
        }

        if (loginUserDTO.auth == null || loginUserDTO.auth.isEmpty()) {
            throw new InvalidLoginException();
        }

        if (!usersRepository.existsByEmailAndAuth(loginUserDTO.email, loginUserDTO.auth)) {
            throw new InvalidLoginException();
        }

        return usersRepository.findByEmailAndAuth(
                loginUserDTO.email,
                loginUserDTO.auth);
    }

    public void validateUser (Long userId, String auth) {
        if (userId == null) {
            throw new InvalidUserException();
        }
        if (auth == null || auth.isEmpty()) {
            throw new InvalidUserException();
        }
        UserEntity userEntity = usersRepository.findByAuthAndId(
                auth,
                userId);

        if (userEntity == null) {
            throw new InvalidUserException();
        }

    }
}
