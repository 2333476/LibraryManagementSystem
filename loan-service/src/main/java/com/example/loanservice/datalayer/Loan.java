package com.example.loanservice.datalayer;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "loans")
public class Loan {

    @Id
    private String id;

    private LoanIdentifier loanIdentifier;
    private BookIdentifier bookIdentifier;
    private CustomerIdentifier customerIdentifier;
    private EmployeeIdentifier employeeIdentifier;

    private LocalDate loanDate;
    private LocalDate returnDate;
    private LoanStatus loanStatus;
    private Double penaltyAmount;
}
