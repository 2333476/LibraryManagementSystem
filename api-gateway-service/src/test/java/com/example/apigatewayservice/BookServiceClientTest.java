package com.example.apigatewayservice;

import com.example.apigatewayservice.domainclientlayer.BookServiceClient;
import com.example.apigatewayservice.presentationlayer.bookdto.BookRequestModel;
import com.example.apigatewayservice.presentationlayer.bookdto.BookResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BookServiceClient bookServiceClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Inject mock URL manually (could also use reflection or constructor with real values)
        bookServiceClient = new BookServiceClient(restTemplate, "localhost", "8083");
    }

    @Test
    void testGetAllBooks() {
        BookResponseModel[] mockBooks = {
                new BookResponseModel("1", "Title 1", "Author A", "ISBN1", 19.99, "2024-01-01"),
                new BookResponseModel("2", "Title 2", "Author B", "ISBN2", 29.99, "2024-02-02")
        };

        ResponseEntity<BookResponseModel[]> response = new ResponseEntity<>(mockBooks, HttpStatus.OK);

        when(restTemplate.getForEntity(anyString(), eq(BookResponseModel[].class))).thenReturn(response);

        List<BookResponseModel> result = bookServiceClient.getAllBooks();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(BookResponseModel[].class));
    }

    @Test
    void testGetBookById() {
        String bookId = "1";
        BookResponseModel mockResponse = new BookResponseModel(
                "1",
                "Clean Architecture",
                "Robert C. Martin",
                "9780134494166",
                49.99,
                "2021-01-15"
        );

        when(restTemplate.getForObject(contains(bookId), eq(BookResponseModel.class))).thenReturn(mockResponse);

        BookResponseModel result = bookServiceClient.getBookById(bookId);

        assertNotNull(result);
        verify(restTemplate).getForObject(contains(bookId), eq(BookResponseModel.class));
    }

    @Test
    void testAddBook() {
        BookRequestModel request = new BookRequestModel();
        BookResponseModel mockResponse = new BookResponseModel(
                "123",
                "Domain-Driven Design",
                "Eric Evans",
                "9780321125217",
                59.99,
                "2003-08-30"
        );
        when(restTemplate.postForObject(anyString(), eq(request), eq(BookResponseModel.class))).thenReturn(mockResponse);

        BookResponseModel result = bookServiceClient.addBook(request);

        assertNotNull(result);
        verify(restTemplate).postForObject(anyString(), eq(request), eq(BookResponseModel.class));
    }

    @Test
    void testUpdateBook() {
        BookRequestModel request = new BookRequestModel();
        String bookId = "123";
        BookResponseModel updated = new BookResponseModel(
                "123",
                "Refactoring",
                "Martin Fowler",
                "9780201485677",
                45.00,
                "1999-07-08"
        );
        ResponseEntity<BookResponseModel> response = new ResponseEntity<>(updated, HttpStatus.OK);

        when(restTemplate.exchange(
                contains(bookId),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(BookResponseModel.class))
        ).thenReturn(response);

        BookResponseModel result = bookServiceClient.updateBook(bookId, request);

        assertNotNull(result);
        verify(restTemplate).exchange(
                contains(bookId),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(BookResponseModel.class));
    }

    @Test
    void testDeleteBook() {
        String bookId = "456";

        doNothing().when(restTemplate).delete(contains(bookId));

        bookServiceClient.deleteBook(bookId);

        verify(restTemplate).delete(contains(bookId));
    }
}
