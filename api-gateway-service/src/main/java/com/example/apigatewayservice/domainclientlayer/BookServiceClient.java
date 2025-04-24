package com.example.apigatewayservice.domainclientlayer;

import com.example.apigatewayservice.presentationlayer.bookdto.BookRequestModel;
import com.example.apigatewayservice.presentationlayer.bookdto.BookResponseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class BookServiceClient {
    private final RestTemplate restTemplate;
    private final String BOOK_BASE_URL;

    public BookServiceClient(RestTemplate restTemplate,
                             @Value("${app.book-service.host}") String host,
                             @Value("${app.book-service.port}") String port) {
        this.restTemplate = restTemplate;
        this.BOOK_BASE_URL = "http://" + host + ":" + port + "/api/v1/books";
    }

    public List<BookResponseModel> getAllBooks() {
        ResponseEntity<BookResponseModel[]> response =
                restTemplate.getForEntity(BOOK_BASE_URL, BookResponseModel[].class);
        return Arrays.asList(response.getBody());
    }

    public BookResponseModel getBookById(String bookId) {
        return restTemplate.getForObject(BOOK_BASE_URL + "/" + bookId, BookResponseModel.class);
    }

    public BookResponseModel addBook(BookRequestModel requestModel) {
        return restTemplate.postForObject(BOOK_BASE_URL, requestModel, BookResponseModel.class);
    }

    public BookResponseModel updateBook(String bookId, BookRequestModel requestModel) {
        HttpEntity<BookRequestModel> entity = new HttpEntity<>(requestModel);
        ResponseEntity<BookResponseModel> response =
                restTemplate.exchange(BOOK_BASE_URL + "/" + bookId, HttpMethod.PUT, entity, BookResponseModel.class);
        return response.getBody();
    }

    public void deleteBook(String bookId) {
        restTemplate.delete(BOOK_BASE_URL + "/" + bookId);
    }
}
