package com.example.loanservice.datamapperlayer.bookdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class BookResponseModel {

    private String bookId;
    private String title;
    private String author;
    private String isbn;
    private Double price;
    private String publishedDate;
}
