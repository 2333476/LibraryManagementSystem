package com.example.bookservice.dataaccesslayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findBookByBookIdentifier_BookId(String bookId);

    boolean existsByIsbn(String isbn);

    Book findBookByIsbn(String isbn);


}
