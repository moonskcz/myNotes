package com.mynotes.mynotes.controllers;

import com.mynotes.mynotes.dtos.request.CreateUserDTO;
import com.mynotes.mynotes.dtos.request.LoginUserDTO;
import com.mynotes.mynotes.dtos.response.UserCreatedDTO;
import com.mynotes.mynotes.dtos.response.UserLoginDTO;
import com.mynotes.mynotes.entities.UserEntity;
import com.mynotes.mynotes.exceptions.UserNotFoundException;
import com.mynotes.mynotes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    @Autowired
    UserService userService;

    @PutMapping("/users")
    public UserCreatedDTO createUser (@RequestBody CreateUserDTO createUserDTO) {
        UserEntity userEntity = userService.createUser(createUserDTO);
        return new UserCreatedDTO(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getAuth()
        );

    }

    @PostMapping("/users")
    public UserLoginDTO loginUser (@RequestBody LoginUserDTO loginUserDTO) {
        UserEntity userEntity = userService.loginUser(loginUserDTO);
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.auth = userEntity.getAuth();
        userLoginDTO.id = userEntity.getId();
        userLoginDTO.succesful = true;
        return userLoginDTO;

    }

}
