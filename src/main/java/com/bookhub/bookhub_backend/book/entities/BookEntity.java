package com.bookhub.bookhub_backend.book.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document("Book")
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {
    @Id
    private String id;
    private String name;
    private String author;
    private String description;
    private List<String> images;
    private String owner;
}
