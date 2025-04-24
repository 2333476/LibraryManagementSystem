package com.example.apigatewayservice.presentationlayer;

import com.example.apigatewayservice.businesslayer.BookService;
import com.example.apigatewayservice.presentationlayer.bookdto.BookRequestModel;
import com.example.apigatewayservice.presentationlayer.bookdto.BookResponseModel;
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
    public ResponseEntity<List<BookResponseModel>> getBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponseModel> getBookById(@PathVariable String bookId) {
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    @PostMapping
    public ResponseEntity<BookResponseModel> addBook(@RequestBody BookRequestModel bookRequestModel) {
        return ResponseEntity.ok(bookService.addBook(bookRequestModel));
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookResponseModel> updateBook(@PathVariable String bookId,
                                                        @RequestBody BookRequestModel bookRequestModel) {
        return ResponseEntity.ok(bookService.updateBook(bookId, bookRequestModel));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable String bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }
}
