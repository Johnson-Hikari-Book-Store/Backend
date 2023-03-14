package com.bookhub.bookhub_backend.book;

import com.bookhub.bookhub_backend.book.dto.CreateBookDto;
import com.bookhub.bookhub_backend.book.dto.UpdateBookDto;
import com.bookhub.bookhub_backend.book.entities.BookEntity;
import com.bookhub.bookhub_backend.book.repositories.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    public BookEntity createNewBook (CreateBookDto createBookDto) {
        BookEntity newBook = BookEntity.builder()
                .author(createBookDto.getAuthor())
                .name(createBookDto.getName())
                .description(createBookDto.getDescription())
                .images(createBookDto.getImages())
                .owner(createBookDto.getOwner())
                .build();
        bookRepository.save(newBook);
        return newBook;
    }

    public List<BookEntity> getAllBooks () {
        List<BookEntity> books = bookRepository.findAll();
        return books;
    }

    public BookEntity getSingleBook(String bookId) throws Exception {
        Optional<BookEntity> book = bookRepository.findById(bookId);
        if(book.isPresent()) {
            return book.get();
        }else {
            throw new Exception("No Book Found");
        }
    }

    public BookEntity updateBook (String bookId, UpdateBookDto updateBookDto) throws Exception {
        BookEntity book = getSingleBook(bookId);
        ObjectMapper mapObject = new ObjectMapper();
        Map<String, Object> bookFields = mapObject.convertValue(updateBookDto, Map.class);
        bookFields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(BookEntity.class, key);
            if( value != null) {
                assert field != null;
                field.setAccessible(true);
                ReflectionUtils.setField(field, book, value);
            }
        });
        bookRepository.save(book);
        return book;
    }

    public void deleteSingleBook ( String bookId) throws Exception {
        BookEntity book = getSingleBook(bookId);
        bookRepository.deleteById(book.getId());
    }
}
