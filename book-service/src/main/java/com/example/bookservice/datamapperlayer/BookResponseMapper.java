package com.example.bookservice.datamapperlayer;

import com.example.bookservice.dataaccesslayer.Book;
import com.example.bookservice.presentationlayer.BookResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookResponseMapper {

    @Mapping(source = "bookIdentifier.bookId", target = "bookId")
    BookResponseModel entityToResponseModel(Book book);

    List<BookResponseModel> entityToResponseModelList(List<Book> books);
}
