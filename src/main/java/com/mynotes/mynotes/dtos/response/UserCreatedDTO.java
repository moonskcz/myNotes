package com.mynotes.mynotes.dtos.response;

public class UserCreatedDTO {

    public Long id;

    public String email;

    public String auth;

    public UserCreatedDTO() {
    }

    public UserCreatedDTO(Long id, String email, String auth) {
        this.id = id;
        this.email = email;
        this.auth = auth;
    }
}
