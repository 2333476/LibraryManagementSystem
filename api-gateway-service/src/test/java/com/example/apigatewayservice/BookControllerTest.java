package com.example.apigatewayservice;

import com.example.apigatewayservice.businesslayer.BookService;
import com.example.apigatewayservice.presentationlayer.BookController;
import com.example.apigatewayservice.presentationlayer.bookdto.BookRequestModel;
import com.example.apigatewayservice.presentationlayer.bookdto.BookResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookControllerTest {
    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookController = new BookController(bookService);
    }

    @Test
    void testGetBooks() {
        List<BookResponseModel> mockList = List.of(
                BookResponseModel.builder()
                        .bookId("book1")
                        .title("Test Book")
                        .author("Test Author")
                        .isbn("123456789")
                        .price(19.99)
                        .publishedDate("2024-01-01")
                        .build()
        );

        when(bookService.getBooks()).thenReturn(mockList);

        ResponseEntity<List<BookResponseModel>> response = bookController.getBooks();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("book1", response.getBody().get(0).getBookId());
        verify(bookService).getBooks();
    }

    @Test
    void testGetBookById() {
        String bookId = "book123";

        BookResponseModel mockBook = BookResponseModel.builder()
                .bookId(bookId)
                .title("Java 101")
                .author("Jane Doe")
                .isbn("987654321")
                .price(29.99)
                .publishedDate("2023-12-01")
                .build();

        when(bookService.getBookById(bookId)).thenReturn(mockBook);

        ResponseEntity<BookResponseModel> response = bookController.getBookById(bookId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(bookId, response.getBody().getBookId());
        verify(bookService).getBookById(bookId);
    }

    @Test
    void testAddBook() {
        BookRequestModel request = new BookRequestModel(
                "New Book", "John Smith", "ISBN-222", 24.99, "2024-02-15"
        );

        BookResponseModel mockResponse = BookResponseModel.builder()
                .bookId("book456")
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishedDate(request.getPublishedDate())
                .build();

        when(bookService.addBook(request)).thenReturn(mockResponse);

        ResponseEntity<BookResponseModel> response = bookController.addBook(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("book456", response.getBody().getBookId());
        verify(bookService).addBook(request);
    }

    @Test
    void testUpdateBook() {
        String bookId = "book-update";
        BookRequestModel request = new BookRequestModel(
                "Updated Title", "Updated Author", "ISBN-333", 15.99, "2024-03-01"
        );

        BookResponseModel updatedBook = BookResponseModel.builder()
                .bookId(bookId)
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishedDate(request.getPublishedDate())
                .build();

        when(bookService.updateBook(bookId, request)).thenReturn(updatedBook);

        ResponseEntity<BookResponseModel> response = bookController.updateBook(bookId, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Title", response.getBody().getTitle());
        verify(bookService).updateBook(bookId, request);
    }

    @Test
    void testDeleteBook() {
        String bookId = "book-delete";

        doNothing().when(bookService).deleteBook(bookId);

        ResponseEntity<Void> response = bookController.deleteBook(bookId);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(bookService).deleteBook(bookId);
    }
}
