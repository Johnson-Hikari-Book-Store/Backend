package com.bookhub.bookhub_backend.user.dto;

import lombok.Data;

@Data
public class CreateNewUserRequestDto {
    private  String name;
    private String email;
    private String username;
    private String password;
}
