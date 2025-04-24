package com.example.bookservice;

import com.example.bookservice.dataaccesslayer.Book;
import com.example.bookservice.dataaccesslayer.BookIdentifier;
import com.example.bookservice.dataaccesslayer.BookRepository;
import com.example.bookservice.presentationlayer.BookRequestModel;
import com.example.bookservice.presentationlayer.BookResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.datasource.url=jdbc:h2:mem:book_test_db"
        })
@ActiveProfiles("h2")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private BookRepository bookRepository;

    private final String BASE_URI = "/api/v1/books";
    private Book existingBook;

    @BeforeEach
    void setUp() {
        BookIdentifier identifier = new BookIdentifier();
        existingBook = new Book(identifier, "Test Title", "Test Author", "999-TEST", 19.99, "2024-01-01");
        bookRepository.save(existingBook);
    }

    // ✅ POSITIVE TESTS

    @Test
    public void getAllBooks_returnsList() {
        webTestClient.get()
                .uri(BASE_URI)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookResponseModel.class)
                .value(books -> assertFalse(books.isEmpty()));
    }

    @Test
    public void getBookById_existingId_returnsBook() {
        webTestClient.get()
                .uri(BASE_URI + "/" + existingBook.getBookIdentifier().getBookId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo("Test Title");
    }

    @Test
    public void addBook_validRequest_returnsCreatedBook() {
        BookRequestModel newBook = new BookRequestModel();
        newBook.setTitle("New Book");
        newBook.setAuthor("Author X");
        newBook.setIsbn("NEW-1234");
        newBook.setPrice(14.99);
        newBook.setPublishedDate("2025-01-01");

        webTestClient.post()
                .uri(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.title").isEqualTo("New Book")
                .jsonPath("$.isbn").isEqualTo("NEW-1234");
    }

    @Test
    public void updateBook_existingId_updatesAndReturnsBook() {
        BookRequestModel update = new BookRequestModel();
        update.setTitle("Updated Title");
        update.setAuthor("Updated Author");
        update.setIsbn("999-TEST");
        update.setPrice(29.99);
        update.setPublishedDate("2025-02-01");

        webTestClient.put()
                .uri(BASE_URI + "/" + existingBook.getBookIdentifier().getBookId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(update)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo("Updated Title")
                .jsonPath("$.price").isEqualTo(29.99);
    }

    @Test
    public void deleteBook_existingId_returnsNoContent() {
        webTestClient.delete()
                .uri(BASE_URI + "/" + existingBook.getBookIdentifier().getBookId())
                .exchange()
                .expectStatus().isNoContent();

        assertFalse(bookRepository.existsById(existingBook.getId()));
    }

    // ❌ NEGATIVE TESTS

    @Test
    public void getBookById_nonExistingId_returnsNotFound() {
        webTestClient.get()
                .uri(BASE_URI + "/non-existent-id-999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void deleteBook_nonExistingId_returnsNotFound() {
        webTestClient.delete()
                .uri(BASE_URI + "/non-existent-id-999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void addBook_missingTitle_returnsBadRequest() {
        BookRequestModel invalidBook = new BookRequestModel();
        invalidBook.setAuthor("Some Author");
        invalidBook.setIsbn("INVALID-123");
        invalidBook.setPrice(9.99);
        invalidBook.setPublishedDate("2025-01-01");

        webTestClient.post()
                .uri(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidBook)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void updateBook_nonExistingId_returnsNotFound() {
        BookRequestModel update = new BookRequestModel();
        update.setTitle("Updated Title");
        update.setAuthor("Author X");
        update.setIsbn("XYZ-987");
        update.setPrice(19.99);
        update.setPublishedDate("2025-12-12");

        webTestClient.put()
                .uri(BASE_URI + "/non-existent-id-999")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(update)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void addBook_existingIsbn_returnsUnprocessableEntity() {
        BookRequestModel duplicateBook = new BookRequestModel();
        duplicateBook.setTitle("Duplicate Book");
        duplicateBook.setAuthor("Author X");
        duplicateBook.setIsbn("999-TEST"); // already exists
        duplicateBook.setPrice(19.99);
        duplicateBook.setPublishedDate("2025-01-01");

        webTestClient.post()
                .uri(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(duplicateBook)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectBody()
                .jsonPath("$.message").value(msg -> assertTrue(((String) msg).toLowerCase().contains("isbn")));
    }

    @Test
    public void getBookById_invalidFormat_returnsNotFound() {
        // bookId is a string, so invalid format still gets passed as param
        webTestClient.get()
                .uri(BASE_URI + "/123") // not a real book ID
                .exchange()
                .expectStatus().isNotFound(); // corrected: not bad request
    }

    @Test
    public void updateBook_missingFields_returnsBadRequest() {
        BookRequestModel invalidUpdate = new BookRequestModel(); // all fields missing

        webTestClient.put()
                .uri(BASE_URI + "/" + existingBook.getBookIdentifier().getBookId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidUpdate)
                .exchange()
                .expectStatus().isBadRequest();
    }
}
