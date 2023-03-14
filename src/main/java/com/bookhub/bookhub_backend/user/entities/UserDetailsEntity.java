package com.bookhub.bookhub_backend.user.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class UserDetailsEntity {
    @Id
    private  String id;
    private String country;
    private String state;
    private String address;
    private String phoneNum;
    private String profileImage;
    private String preferredCommunicationMeans;
}
