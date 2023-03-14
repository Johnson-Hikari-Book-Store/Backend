package com.bookhub.bookhub_backend.book.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateBookDto {
    private String name;
    private String author;
    private String description;
    private List<String> images;
    private String owner;
}
