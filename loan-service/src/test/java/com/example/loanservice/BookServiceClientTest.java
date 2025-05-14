package com.example.loanservice;

import com.example.loanservice.datamapperlayer.bookdto.BookResponseModel;
import com.example.loanservice.domainclientlayer.BookServiceClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceClientTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final BookServiceClient client =
            new BookServiceClient(restTemplate, objectMapper, "localhost", "8083");

    @Test
    void shouldThrowExceptionWhenBookNotFound() {
        String id = "invalid-id";
        when(restTemplate.getForObject("http://localhost:8083/api/v1/books/" + id, BookResponseModel.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(RuntimeException.class, () -> client.getBookByBookId(id));
    }

    @Test
    void shouldReturnBookWhenFound() {
        String id = "valid-id";
        BookResponseModel mockBook = new BookResponseModel();
        mockBook.setBookId(id);
        mockBook.setTitle("Effective Java");
        mockBook.setAuthor("Joshua Bloch");

        when(restTemplate.getForObject("http://localhost:8083/api/v1/books/" + id, BookResponseModel.class))
                .thenReturn(mockBook);

        BookResponseModel result = client.getBookByBookId(id);
        assertEquals("Effective Java", result.getTitle());
        assertEquals("Joshua Bloch", result.getAuthor());
    }
}
