package com.bookhub.bookhub_backend.book;

import com.bookhub.bookhub_backend.book.dto.CreateBookDto;
import com.bookhub.bookhub_backend.book.dto.UpdateBookDto;
import com.bookhub.bookhub_backend.book.entities.BookEntity;
import com.bookhub.bookhub_backend.user.dto.UpdateUserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api/v1/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping()
    public ResponseEntity<Map<String, Object>> getAllBooks () {
        Map<String, Object> response = new HashMap<>();
        List<BookEntity> books = bookService.getAllBooks();
        response.put("success", true);
        response.put("message", "books fetched successfully");
        response.put("data", books);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> getAllBooks (@Valid @RequestBody CreateBookDto createBookDto) {
        Map<String, Object> response = new HashMap<>();
      BookEntity book = bookService.createNewBook(createBookDto);
        response.put("success", true);
        response.put("message", "book created successfully");
        response.put("data", book);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{bookId}")
    public   ResponseEntity<Map<String, Object>> getSingleBook (
            @PathVariable String bookId
    ) throws Exception {
        Map<String, Object> response = new HashMap<>();
        BookEntity book = bookService.getSingleBook(bookId);
        response.put("success", true);
        response.put("message", "book fetched successfully");
        response.put("data", book);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{bookId}")
    public   ResponseEntity<Map<String, Object>> updateBook (
            @PathVariable String bookId,
            @Valid @RequestBody UpdateBookDto updateBookDto
            ) throws Exception {
        Map<String, Object> response = new HashMap<>();
        BookEntity book = bookService.updateBook(bookId, updateBookDto);
        response.put("success", true);
        response.put("message", "book updated successfully");
        response.put("data", book);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{bookId}")
    public   ResponseEntity<Map<String, Object>> deleteBook (
            @PathVariable String bookId
    ) throws Exception {
        Map<String, Object> response = new HashMap<>();
       bookService.deleteSingleBook(bookId);
        response.put("success", true);
        response.put("message", "book fetched successfully");
        return ResponseEntity.ok(response);
    }
}
