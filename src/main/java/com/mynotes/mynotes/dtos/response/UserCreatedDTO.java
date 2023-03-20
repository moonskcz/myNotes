package com.mynotes.mynotes.dtos.response;

public class UserCreatedDTO {

    public Long id;

    public String email;

    public String name;

    public String auth;

    public UserCreatedDTO() {
    }

    public UserCreatedDTO(Long id, String email, String name, String auth) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.auth = auth;
    }
}
