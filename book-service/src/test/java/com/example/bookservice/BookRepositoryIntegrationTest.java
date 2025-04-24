package com.example.bookservice;

import com.example.bookservice.dataaccesslayer.Book;
import com.example.bookservice.dataaccesslayer.BookIdentifier;
import com.example.bookservice.dataaccesslayer.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookRepositoryIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    private Book savedBook;

    @BeforeEach
    void setUp() {
        BookIdentifier bookIdentifier = new BookIdentifier();
        Book book = new Book(bookIdentifier,
                "Test Title",
                "Test Author",
                "1234567890",  // 👈 Ceci est l'ISBN à utiliser dans les tests
                24.99,
                "2024-01-01");

        savedBook = bookRepository.save(book);
    }

    @Test
    void whenBookExists_thenReturnBookByBookId() {
        Book foundBook = bookRepository.findBookByBookIdentifier_BookId(savedBook.getBookIdentifier().getBookId());

        assertNotNull(foundBook);
        assertEquals(savedBook.getTitle(), foundBook.getTitle());
        assertEquals(savedBook.getAuthor(), foundBook.getAuthor());
        assertEquals(savedBook.getIsbn(), foundBook.getIsbn());
    }

    @Test
    void whenBookIdDoesNotExist_thenReturnNull() {
        String nonExistentBookId = "non-existent-book-id-123";

        Book foundBook = bookRepository.findBookByBookIdentifier_BookId(nonExistentBookId);

        assertNull(foundBook, "Expected null when bookId does not exist");
    }

    @Test
    void existsByIsbn_existingIsbn_returnsTrue() {
        assertTrue(bookRepository.existsByIsbn(savedBook.getIsbn()));
    }

    @Test
    void existsByIsbn_nonExistingIsbn_returnsFalse() {
        assertFalse(bookRepository.existsByIsbn("NOPE-123"));
    }

    @Test
    void findBookByIsbn_existingIsbn_returnsBook() {
        Book found = bookRepository.findBookByIsbn(savedBook.getIsbn());

        assertNotNull(found);
        assertEquals(savedBook.getTitle(), found.getTitle());
        assertEquals(savedBook.getAuthor(), found.getAuthor());
    }

    @Test
    void findBookByIsbn_nonExistingIsbn_returnsNull() {
        Book found = bookRepository.findBookByIsbn("NOT-FOUND-000");
        assertNull(found);
    }
}
