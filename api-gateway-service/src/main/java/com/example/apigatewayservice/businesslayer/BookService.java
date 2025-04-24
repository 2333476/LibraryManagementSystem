package com.example.apigatewayservice.businesslayer;

import com.example.apigatewayservice.presentationlayer.bookdto.BookRequestModel;
import com.example.apigatewayservice.presentationlayer.bookdto.BookResponseModel;

import java.util.List;

public interface BookService {

    List<BookResponseModel> getBooks();

    BookResponseModel getBookById(String bookId);

    BookResponseModel addBook(BookRequestModel bookRequestModel);

    BookResponseModel updateBook(String bookId, BookRequestModel bookRequestModel);

    void deleteBook(String bookId);
}
