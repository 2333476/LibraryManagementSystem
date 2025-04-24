package com.example.bookservice.presentationlayer;

import com.example.bookservice.businesslogiclayer.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookResponseModel>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponseModel> getBookById(@PathVariable String bookId) {
        return ResponseEntity.ok(bookService.getBookByBookId(bookId));
    }

    @PostMapping
    public ResponseEntity<BookResponseModel> addBook(@Valid @RequestBody BookRequestModel bookRequestModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(bookRequestModel));
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookResponseModel> updateBook(@PathVariable String bookId,
                                                        @Valid @RequestBody BookRequestModel bookRequestModel) {
        return ResponseEntity.ok(bookService.updateBook(bookId, bookRequestModel));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable String bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }
}
