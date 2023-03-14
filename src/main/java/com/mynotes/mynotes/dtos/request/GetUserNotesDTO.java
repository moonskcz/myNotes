package com.mynotes.mynotes.dtos.request;

public class GetUserNotesDTO {

    public Long userId;

    public String auth;

    //how many notes to return
    public Integer count = 20;

    //how many notes to ignore
    public Integer offset = 0;

}
