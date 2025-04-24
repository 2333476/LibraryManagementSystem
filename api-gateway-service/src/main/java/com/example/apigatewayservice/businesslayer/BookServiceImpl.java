package com.example.apigatewayservice.businesslayer;

import com.example.apigatewayservice.domainclientlayer.BookServiceClient;
import com.example.apigatewayservice.presentationlayer.bookdto.BookRequestModel;
import com.example.apigatewayservice.presentationlayer.bookdto.BookResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookServiceClient bookServiceClient;

    public BookServiceImpl(BookServiceClient bookServiceClient) {
        this.bookServiceClient = bookServiceClient;
    }

    public List<BookResponseModel> getBooks() {
        return bookServiceClient.getAllBooks();
    }

    public BookResponseModel getBookById(String bookId) {
        return bookServiceClient.getBookById(bookId);
    }

    public BookResponseModel addBook(BookRequestModel requestModel) {
        return bookServiceClient.addBook(requestModel);
    }

    public BookResponseModel updateBook(String bookId, BookRequestModel requestModel) {
        return bookServiceClient.updateBook(bookId, requestModel);
    }

    public void deleteBook(String bookId) {
        bookServiceClient.deleteBook(bookId);
    }
}
