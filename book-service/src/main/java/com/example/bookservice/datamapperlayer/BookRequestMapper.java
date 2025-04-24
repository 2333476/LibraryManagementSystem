package com.example.bookservice.datamapperlayer;

import com.example.bookservice.dataaccesslayer.Book;
import com.example.bookservice.dataaccesslayer.BookIdentifier;
import com.example.bookservice.presentationlayer.BookRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface BookRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookIdentifier", source = "bookIdentifier")
    Book requestModelToEntity(BookRequestModel bookRequestModel, BookIdentifier bookIdentifier);
}
