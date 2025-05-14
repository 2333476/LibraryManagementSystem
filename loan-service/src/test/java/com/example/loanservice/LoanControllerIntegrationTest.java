package com.example.loanservice;

import com.example.loanservice.datalayer.*;
import com.example.loanservice.datamapperlayer.bookdto.BookResponseModel;
import com.example.loanservice.datamapperlayer.customerdto.CustomerResponseModel;
import com.example.loanservice.datamapperlayer.employeedto.EmployeeResponseModel;
import com.example.loanservice.domainclientlayer.BookServiceClient;
import com.example.loanservice.domainclientlayer.CustomerServiceClient;
import com.example.loanservice.domainclientlayer.EmployeeServiceClient;
import com.example.loanservice.datalayer.LoanRepository;
import com.example.loanservice.presentationlayer.LoanRequestModel;
import com.example.loanservice.presentationlayer.LoanResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoanControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookServiceClient bookServiceClient;

    @MockBean
    private CustomerServiceClient customerServiceClient;

    @MockBean
    private EmployeeServiceClient employeeServiceClient;

    private final String BASE_URL = "/api/v1/loans";
    private final String VALID_BOOK_ID = "04c2e242-8d2f-4a1e-a7aa-b11a17d84f9e";
    private final String VALID_CUSTOMER_ID = "623e4567-e89b-12d3-a456-556642440005";
    private final String VALID_EMPLOYEE_ID = "3b8afd72-75eb-4feb-bdb6-2471550cdf47";

    @BeforeEach
    void setup() {
        loanRepository.deleteAll();

        BookResponseModel book = new BookResponseModel();
        book.setBookId(VALID_BOOK_ID);
        book.setTitle("Design Patterns");
        book.setAuthor("Erich Gamma");

        when(bookServiceClient.getBookByBookId(VALID_BOOK_ID)).thenReturn(book);

        CustomerResponseModel customer = new CustomerResponseModel();
        customer.setCustomerId(VALID_CUSTOMER_ID);
        customer.setFirstName("Alisha");
        customer.setLastName("Singh");

        when(customerServiceClient.getCustomerByCustomerId(VALID_CUSTOMER_ID)).thenReturn(customer);

        EmployeeResponseModel employee = new EmployeeResponseModel();
        employee.setEmployeeId(VALID_EMPLOYEE_ID);
        employee.setFirstName("Gayelord");
        employee.setLastName("Wrenn");

        when(employeeServiceClient.getEmployeeByEmployeeId(VALID_EMPLOYEE_ID)).thenReturn(employee);
    }

    @Test
    @DisplayName("✔ Should add a new loan successfully")
    void shouldAddLoan() {
        LoanRequestModel request = new LoanRequestModel(
                VALID_BOOK_ID, VALID_CUSTOMER_ID, VALID_EMPLOYEE_ID,
                LocalDate.of(2025, 5, 13), LocalDate.of(2025, 6, 13)
        );

        webTestClient.post()
                .uri(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    @DisplayName("✔ Should return all loans")
    void shouldGetAllLoans() {
        Loan loan = Loan.builder()
                .loanIdentifier(new LoanIdentifier("test-loan-123"))
                .bookIdentifier(new BookIdentifier(VALID_BOOK_ID))
                .customerIdentifier(new CustomerIdentifier(VALID_CUSTOMER_ID))
                .employeeIdentifier(new EmployeeIdentifier(VALID_EMPLOYEE_ID))
                .loanDate(LocalDate.of(2025, 5, 13))
                .returnDate(LocalDate.of(2025, 6, 13))
                .loanStatus(LoanStatus.ACTIVE)
                .penaltyAmount(0.0)
                .build();

        loanRepository.save(loan);

        webTestClient.get()
                .uri(BASE_URL)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(LoanResponseModel.class)
                .value(loans -> assertThat(loans).hasSize(1));
    }

    @Test
    @DisplayName("✖ Should return 404 when loanId not found")
    void shouldReturnNotFoundWhenInvalidId() {
        webTestClient.get()
                .uri(BASE_URL + "/non-existent-id")
                .exchange()
                .expectStatus().isNotFound();  // <-- Échec ici si @ControllerAdvice est absent
    }

    @Test
    @DisplayName("✔ Should update an existing loan")
    void shouldUpdateLoan() {
        Loan loan = loanRepository.save(
                Loan.builder()
                        .loanIdentifier(new LoanIdentifier("update-id"))
                        .bookIdentifier(new BookIdentifier(VALID_BOOK_ID))
                        .customerIdentifier(new CustomerIdentifier(VALID_CUSTOMER_ID))
                        .employeeIdentifier(new EmployeeIdentifier(VALID_EMPLOYEE_ID))
                        .loanDate(LocalDate.of(2025, 5, 1))
                        .returnDate(LocalDate.of(2025, 6, 1))
                        .loanStatus(LoanStatus.ACTIVE)
                        .penaltyAmount(0.0)
                        .build()
        );

        LoanRequestModel update = new LoanRequestModel(
                VALID_BOOK_ID, VALID_CUSTOMER_ID, VALID_EMPLOYEE_ID,
                LocalDate.of(2025, 5, 1), LocalDate.of(2025, 6, 20)
        );

        webTestClient.put()
                .uri(BASE_URL + "/" + loan.getLoanIdentifier().getLoanId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(update)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("✔ Should delete an existing loan")
    void shouldDeleteLoan() {
        Loan loan = loanRepository.save(
                Loan.builder()
                        .loanIdentifier(new LoanIdentifier("delete-id"))
                        .bookIdentifier(new BookIdentifier(VALID_BOOK_ID))
                        .customerIdentifier(new CustomerIdentifier(VALID_CUSTOMER_ID))
                        .employeeIdentifier(new EmployeeIdentifier(VALID_EMPLOYEE_ID))
                        .loanDate(LocalDate.of(2025, 5, 1))
                        .returnDate(LocalDate.of(2025, 6, 1))
                        .loanStatus(LoanStatus.ACTIVE)
                        .penaltyAmount(0.0)
                        .build()
        );

        webTestClient.delete()
                .uri(BASE_URL + "/" + loan.getLoanIdentifier().getLoanId())
                .exchange()
                .expectStatus().isNoContent();   // ✅ Correspond à @ResponseStatus(HttpStatus.NO_CONTENT)

    }
}
