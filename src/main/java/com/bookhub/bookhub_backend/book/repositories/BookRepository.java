package com.bookhub.bookhub_backend.book.repositories;

import com.bookhub.bookhub_backend.book.entities.BookEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<BookEntity, String> {
}
