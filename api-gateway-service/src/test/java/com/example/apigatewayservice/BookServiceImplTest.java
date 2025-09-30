package com.example.apigatewayservice;
import com.example.apigatewayservice.businesslayer.BookServiceImpl;
import com.example.apigatewayservice.domainclientlayer.BookServiceClient;
import com.example.apigatewayservice.presentationlayer.bookdto.BookRequestModel;
import com.example.apigatewayservice.presentationlayer.bookdto.BookResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookServiceImplTest {
    private BookServiceClient bookServiceClient;
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        bookServiceClient = mock(BookServiceClient.class);
        bookService = new BookServiceImpl(bookServiceClient);
    }

    @Test
    void getBooks_shouldReturnListOfBooks() {
        // Arrange
        BookResponseModel book = BookResponseModel.builder()
                .bookId("book123")
                .title("Java Programming")
                .author("John Doe")
                .isbn("1234567890")
                .price(59.99)
                .publishedDate("2023-01-01")
                .build();

        List<BookResponseModel> expectedList = List.of(book);
        when(bookServiceClient.getAllBooks()).thenReturn(expectedList);

        // Act
        List<BookResponseModel> actualList = bookService.getBooks();

        // Assert
        assertEquals(expectedList, actualList);
        verify(bookServiceClient).getAllBooks();
    }

    @Test
    void getBookById_shouldReturnBook() {
        String bookId = "book456";

        BookResponseModel expected = BookResponseModel.builder()
                .bookId(bookId)
                .title("Spring Boot in Action")
                .author("Craig Walls")
                .isbn("9876543210")
                .price(45.00)
                .publishedDate("2022-08-15")
                .build();

        when(bookServiceClient.getBookById(bookId)).thenReturn(expected);

        BookResponseModel actual = bookService.getBookById(bookId);

        assertEquals(expected, actual);
        verify(bookServiceClient).getBookById(bookId);
    }

    @Test
    void addBook_shouldReturnCreatedBook() {
        BookRequestModel request = new BookRequestModel(
                "Clean Code",
                "Robert C. Martin",
                "9780132350884",
                42.00,
                "2008-08-11"
        );

        BookResponseModel expected = BookResponseModel.builder()
                .bookId("book789")
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishedDate(request.getPublishedDate())
                .build();

        when(bookServiceClient.addBook(request)).thenReturn(expected);

        BookResponseModel actual = bookService.addBook(request);

        assertEquals(expected, actual);
        verify(bookServiceClient).addBook(request);
    }

    @Test
    void updateBook_shouldReturnUpdatedBook() {
        String bookId = "book999";
        BookRequestModel request = new BookRequestModel(
                "Refactoring",
                "Martin Fowler",
                "9780201485677",
                47.99,
                "2018-11-20"
        );

        BookResponseModel expected = BookResponseModel.builder()
                .bookId(bookId)
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishedDate(request.getPublishedDate())
                .build();

        when(bookServiceClient.updateBook(bookId, request)).thenReturn(expected);

        BookResponseModel actual = bookService.updateBook(bookId, request);

        assertEquals(expected, actual);
        verify(bookServiceClient).updateBook(bookId, request);
    }

    @Test
    void deleteBook_shouldCallDeleteMethod() {
        String bookId = "book101";
        doNothing().when(bookServiceClient).deleteBook(bookId);

        bookService.deleteBook(bookId);

        verify(bookServiceClient).deleteBook(bookId);
    }
}
