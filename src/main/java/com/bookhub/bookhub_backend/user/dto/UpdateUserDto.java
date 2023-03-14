package com.bookhub.bookhub_backend.user.dto;

import lombok.Data;

@Data
public class UpdateUserDto {
    private  String name;
    private String email;
    private String username;
    private String password;

    private String country;
    private String state;
    private String address;
    private String phoneNum;
    private String profileImage;
    private String prefferedCommunicationMeans;

}
